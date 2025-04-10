package com.musinsam.userservice.app.presentation.v1;

import static com.musinsam.userservice.app.global.response.AuthResponseCode.USER_LOGIN_SUCCESS;
import static com.musinsam.userservice.app.global.response.AuthResponseCode.USER_LOGOUT_SUCCESS;
import static com.musinsam.userservice.app.global.response.AuthResponseCode.USER_SIGNUP_SUCCESS;
import static com.musinsam.userservice.app.global.response.AuthResponseCode.USER_TOKEN_GENERATION_SUCCESS;
import static com.musinsam.userservice.app.global.response.AuthResponseCode.USER_TOKEN_VALIDATION_SUCCESS;

import com.musinsam.common.response.ApiResponse;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthGenerateTokenDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthLogoutDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthSignupDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthValidateTokenDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthGenerateTokenDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthSignupDtoApiV1;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/auth")
@RestController
public class AuthControllerApiV1 {

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<ResAuthSignupDtoApiV1>> signup(
      @RequestBody ReqAuthSignupDtoApiV1 reqAuthSignupDtoApiV1
  ) {
    return ResponseEntity.ok(
        new ApiResponse<>(
            USER_SIGNUP_SUCCESS.getCode(), // 0, -1
            USER_SIGNUP_SUCCESS.getMessage(), // message
            null // 실제 들어갈  값
        ));
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<ResAuthLoginDtoApiV1>> login(
      @RequestBody ReqAuthLoginDtoApiV1 reqAuthLoginDtoApiV1
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        USER_LOGIN_SUCCESS.getCode(),
        USER_LOGIN_SUCCESS.getMessage(),
        null
    ));
  }

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(
      @RequestBody ReqAuthLogoutDtoApiV1 reqAuthLogoutDtoApiV1
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        USER_LOGOUT_SUCCESS.getCode(),
        USER_LOGOUT_SUCCESS.getMessage(),
        null
    ));
  }

  @PostMapping("/generate-token")
  public ResponseEntity<ApiResponse<ResAuthGenerateTokenDtoApiV1>> generateToken(
      @RequestBody ReqAuthGenerateTokenDtoApiV1 reqAuthGenerateTokenDtoApiV1
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        USER_TOKEN_GENERATION_SUCCESS.getCode(),
        USER_TOKEN_GENERATION_SUCCESS.getMessage(),
        null
    ));
  }

  @PostMapping("/validate-token")
  public ResponseEntity<ApiResponse<Void>> validateToken(
      @RequestBody ReqAuthValidateTokenDtoApiV1 reqAuthValidateTokenDtoApiV1
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        USER_TOKEN_VALIDATION_SUCCESS.getCode(),
        USER_TOKEN_VALIDATION_SUCCESS.getMessage(),
        null
    ));
  }

}