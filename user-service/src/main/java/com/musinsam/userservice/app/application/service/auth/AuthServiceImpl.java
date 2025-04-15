package com.musinsam.userservice.app.application.service.auth;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthSignupDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthGenerateTokenDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthGenerateWithCookieDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthLoginWithCookieDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthSignupDtoApiV1;
import com.musinsam.userservice.app.domain.auth.jwt.JwtProvider;
import com.musinsam.userservice.app.domain.auth.vo.AuthErrorCode;
import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.musinsam.userservice.app.domain.user.repository.auth.AuthRepository;
import com.musinsam.userservice.app.domain.user.repository.token.RefreshTokenRepository;
import com.musinsam.userservice.app.domain.user.vo.UserRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final AuthRepository authRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  private final String PREFIX_BEARER_NAME = "Bearer ";

  private void existsByEmail(String email) {
    if (authRepository.existsByEmail(email)) {
      throw new CustomException(AuthErrorCode.DUPLICATE_EMAIL);
    }
  }

  @Transactional
  @Override
  public ResAuthSignupDtoApiV1 signup(ReqAuthSignupDtoApiV1 request) {
    existsByEmail(request.getUser().getEmail());

    UserEntity userEntity = UserEntity.of(
        request.getUser().getEmail(),
        passwordEncoder.encode(request.getUser().getPassword()),
        request.getUser().getName(),
        request.getUser().getUserRoleType()
    );

    UserEntity savedUser = authRepository.save(userEntity);

    return ResAuthSignupDtoApiV1.of(savedUser);
  }

  public ResAuthLoginWithCookieDtoApiV1 login(ReqAuthLoginDtoApiV1 request) {
    UserEntity user = authRepository.findByEmail(request.getUser().getEmail())
        .orElseThrow(() -> new CustomException(AuthErrorCode.INVALID_LOGIN));

    if (!passwordEncoder.matches(request.getUser().getPassword(), user.getPassword())) {
      throw new CustomException(AuthErrorCode.INVALID_LOGIN);
    }

    String accessToken = jwtProvider.createAccessToken(user.getId(), user.getUserRoleType());
    String refreshToken = jwtProvider.createRefreshToken(user.getId(), user.getUserRoleType());

    refreshTokenRepository.saveRefreshToken(user.getId(), refreshToken, jwtProvider.getRefreshTokenExpiration());

    ResponseCookie refreshCookie = getResponseCookie(refreshToken, jwtProvider.getRefreshTokenExpiration() / 1000);

    return ResAuthLoginWithCookieDtoApiV1.of(ResAuthLoginDtoApiV1.of(user, accessToken), refreshCookie);
  }

  private ResponseCookie getResponseCookie(String refreshToken, Long maxAge) {
    return ResponseCookie.from("refresh_token", refreshToken)
        .httpOnly(jwtProvider.getHttpOnly())
        .secure(jwtProvider.getSecure())
        .path(jwtProvider.getPath())
        .domain(jwtProvider.getDomain())
        .sameSite(jwtProvider.getSameSite())
        .maxAge(maxAge)
        .build();
  }

  @Transactional
  @Override
  public ResponseCookie logout(String bearerToken, CurrentUserDtoApiV1 request) {
    String accessToken = bearerToken.replace(PREFIX_BEARER_NAME, "");

    refreshTokenRepository.deleteRefreshToken(request.userId());

    Long expiration = jwtProvider.getRemainingTimeToken(accessToken);
    refreshTokenRepository.setAccessTokenBlacklist(accessToken, expiration);

    return getResponseCookie("", 0L);
  }

  @Override
  public ResAuthGenerateWithCookieDtoApiV1 generateToken(String refreshToken, String bearerToken) {
    if (!jwtProvider.validateToken(refreshToken)) {
      throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }
    String accessToken = bearerToken.replace(PREFIX_BEARER_NAME, "");

    Long userId = jwtProvider.getUserIdFromToken(accessToken);
    UserRoleType role = jwtProvider.getUserRoleFromToken(accessToken);

    String storedRefreshToken = refreshTokenRepository.getRefreshToken(userId)
        .orElseThrow(() -> new CustomException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

    if (!storedRefreshToken.equals(refreshToken)) {
      throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }

    Long remainingTimeToken = jwtProvider.getRemainingTimeToken(accessToken);
    refreshTokenRepository.setAccessTokenBlacklist(accessToken, remainingTimeToken);

    String newAccessToken = jwtProvider.createAccessToken(userId, role);
    String newRefreshToken = jwtProvider.createRefreshToken(userId, role);

    refreshTokenRepository.saveRefreshToken(userId, newRefreshToken, jwtProvider.getRefreshTokenExpiration());

    ResponseCookie refreshCookie = getResponseCookie(newRefreshToken, jwtProvider.getRefreshTokenExpiration() / 1000);

    return ResAuthGenerateWithCookieDtoApiV1.of(ResAuthGenerateTokenDtoApiV1.of(newAccessToken), refreshCookie);
  }
}
