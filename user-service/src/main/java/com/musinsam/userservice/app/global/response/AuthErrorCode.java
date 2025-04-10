package com.musinsam.userservice.app.global.response;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum AuthErrorCode implements ErrorCode {

  // Signup Errors
  DUPLICATE_USERNAME(HttpStatus.CONFLICT.value(), "Username already exists.", HttpStatus.CONFLICT),
  DUPLICATE_EMAIL(HttpStatus.CONFLICT.value(), "Email already exists.", HttpStatus.CONFLICT),
  INVALID_USERNAME_FORMAT(HttpStatus.BAD_REQUEST.value(), "Invalid username format.", HttpStatus.BAD_REQUEST),
  INVALID_SLACK_ID_FORMAT(HttpStatus.BAD_REQUEST.value(), "Invalid Slack ID format.", HttpStatus.BAD_REQUEST),
  PASSWORD_TOO_WEAK(HttpStatus.BAD_REQUEST.value(), "Password does not meet security requirements.", HttpStatus.BAD_REQUEST),
  MISSING_REQUIRED_FIELDS(HttpStatus.BAD_REQUEST.value(), "Missing required fields.", HttpStatus.BAD_REQUEST),

  // Sign-in Errors
  INVALID_LOGIN(HttpStatus.BAD_REQUEST.value(), "Invalid username or password.", HttpStatus.BAD_REQUEST),
  INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(), "Invalid password.", HttpStatus.BAD_REQUEST),
  REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST.value(), "Refresh token has expired.", HttpStatus.BAD_REQUEST),
  INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST.value(), "Invalid refresh token.", HttpStatus.BAD_REQUEST);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
