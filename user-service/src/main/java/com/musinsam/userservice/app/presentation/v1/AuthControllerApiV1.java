package com.musinsam.userservice.app.presentation.v1;

import static com.musinsam.userservice.app.domain.auth.vo.AuthResponseCode.USER_LOGIN_SUCCESS;
import static com.musinsam.userservice.app.domain.auth.vo.AuthResponseCode.USER_LOGOUT_SUCCESS;
import static com.musinsam.userservice.app.domain.auth.vo.AuthResponseCode.USER_SIGNUP_SUCCESS;
import static com.musinsam.userservice.app.domain.auth.vo.AuthResponseCode.USER_TOKEN_GENERATION_SUCCESS;
import static com.musinsam.userservice.app.domain.auth.vo.AuthResponseCode.USER_TOKEN_VALIDATION_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.common.user.UserRoleType;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthSignupDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthGenerateTokenDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthGenerateWithCookieDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthLoginWithCookieDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthSignupDtoApiV1;
import com.musinsam.userservice.app.application.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@RestController
public class AuthControllerApiV1 {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<ResAuthSignupDtoApiV1>> signup(
      @RequestBody ReqAuthSignupDtoApiV1 request
  ) {

    ResAuthSignupDtoApiV1 response = authService.signup(request);

    return ResponseEntity.ok(
        new ApiResponse<>(
            USER_SIGNUP_SUCCESS.getCode(),
            USER_SIGNUP_SUCCESS.getMessage(),
            response
        ));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<ResAuthLoginDtoApiV1>> login(
      @RequestBody ReqAuthLoginDtoApiV1 request,
      HttpServletResponse servletResponse
  ) {

    ResAuthLoginWithCookieDtoApiV1 response = authService.login(request);
    servletResponse.setHeader("Set-Cookie", response.getRefreshCookie().toString());

    return ResponseEntity.ok(new ApiResponse<>(
        USER_LOGIN_SUCCESS.getCode(),
        USER_LOGIN_SUCCESS.getMessage(),
        response.getResAuthLoginDtoApiV1()
    ));
  }

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(
      @RequestHeader("Authorization") String bearerToken,
      @CurrentUser CurrentUserDtoApiV1 request,
      HttpServletResponse servletResponse
  ) {

    ResponseCookie response = authService.logout(bearerToken, request);
    servletResponse.setHeader("Set-Cookie", response.toString());

    return ResponseEntity.ok(new ApiResponse<>(
        USER_LOGOUT_SUCCESS.getCode(),
        USER_LOGOUT_SUCCESS.getMessage(),
        null
    ));
  }

  @PostMapping("/generate-token")
  public ResponseEntity<ApiResponse<ResAuthGenerateTokenDtoApiV1>> generateToken(
      @CookieValue("refresh_token") String refreshToken,
      @RequestHeader("Authorization") String bearerToken,
      HttpServletResponse servletResponse) {

    ResAuthGenerateWithCookieDtoApiV1 response = authService.generateToken(refreshToken, bearerToken);
    servletResponse.setHeader("Set-Cookie", response.getRefreshCookie().toString());

    return ResponseEntity.ok(new ApiResponse<>(
        USER_TOKEN_GENERATION_SUCCESS.getCode(),
        USER_TOKEN_GENERATION_SUCCESS.getMessage(),
        response.getResAuthGenerateTokenDtoApiV1()
    ));
  }

  @CustomPreAuthorize(userRoleType = {UserRoleType.ROLE_USER})
  @PostMapping("/validate-token")
  public ResponseEntity<ApiResponse<String>> validateToken(
      @CurrentUser CurrentUserDtoApiV1 request
  ) {
    Long userId = request.userId();

    return ResponseEntity.ok(new ApiResponse<>(
        USER_TOKEN_VALIDATION_SUCCESS.getCode(),
        USER_TOKEN_VALIDATION_SUCCESS.getMessage(),
        userId.toString()
    ));
  }
}