package com.musinsam.userservice.app.application.service.auth;

import com.musinsam.common.exception.CustomException;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthGenerateTokenDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthSignupDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthGenerateTokenDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthSignupDtoApiV1;
import com.musinsam.userservice.app.application.service.redis.RedisService;
import com.musinsam.userservice.app.domain.auth.jwt.JwtProvider;
import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.musinsam.userservice.app.domain.user.repository.auth.AuthRepository;
import com.musinsam.userservice.app.domain.user.vo.UserRoleType;
import com.musinsam.userservice.app.global.response.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final RedisService redisService;
  private final AuthRepository authRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

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
        request.getUser().getName()
    );

    UserEntity savedUser = authRepository.save(userEntity);

    return ResAuthSignupDtoApiV1.of(savedUser);
  }

  public ResAuthLoginDtoApiV1 login(ReqAuthLoginDtoApiV1 request) {
    UserEntity user = authRepository.findByEmail(request.getUser().getEmail())
        .orElseThrow(() -> new CustomException(AuthErrorCode.INVALID_LOGIN));

    if (!passwordEncoder.matches(request.getUser().getPassword(), user.getPassword())) {
      throw new CustomException(AuthErrorCode.INVALID_LOGIN);
    }

    String accessToken = jwtProvider.createAccessToken(user.getId(), user.getUserRoleType());
    String refreshToken = jwtProvider.createRefreshToken(user.getId(), user.getUserRoleType());

    redisService.saveRefreshToken(user.getId(), refreshToken, jwtProvider.getRefreshTokenExpiration());

    return ResAuthLoginDtoApiV1.of(user, accessToken, refreshToken);
  }

  @Override
  public ResAuthGenerateTokenDtoApiV1 generateToken(ReqAuthGenerateTokenDtoApiV1 request) {
    if (!jwtProvider.validateToken(request.getRefreshToken())) {
      throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }

    Long userId = jwtProvider.getUserIdFromToken(request.getRefreshToken());
    UserRoleType role = jwtProvider.getUserRoleFromToken(request.getRefreshToken());

    String storedRefreshToken = redisService.getRefreshToken(userId)
        .orElseThrow(() -> new CustomException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND));

    if (!storedRefreshToken.equals(request.getRefreshToken())) {
      throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }

    String newAccessToken = jwtProvider.createAccessToken(userId, role);
    String newRefreshToken = jwtProvider.createRefreshToken(userId, role);

    redisService.saveRefreshToken(userId, newRefreshToken, jwtProvider.getRefreshTokenExpiration());

    return ResAuthGenerateTokenDtoApiV1.of(newAccessToken, newRefreshToken);
  }
}
