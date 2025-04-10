package com.musinsam.userservice.app.global.response;

import static org.springframework.http.HttpStatus.OK;

import com.musinsam.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthResponseCode implements SuccessCode {


  USER_SIGNUP_SUCCESS(0, "User signup completed successfully.", OK),
  USER_LOGIN_SUCCESS(0, "User login completed successfully.", OK),
  USER_LOGOUT_SUCCESS(0, "User logout completed successfully.", OK),
  USER_TOKEN_GENERATION_SUCCESS(0, "Token generated successfully.", OK),
  USER_TOKEN_VALIDATION_SUCCESS(0, "Token validated successfully.", OK);


  private final Integer code;
  private final String message;
  private final HttpStatus HttpStatus; // 200

}
