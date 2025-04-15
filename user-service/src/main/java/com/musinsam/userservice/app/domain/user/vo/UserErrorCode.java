package com.musinsam.userservice.app.domain.user.vo;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCode {

  USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "User not found.", HttpStatus.NOT_FOUND),
  USER_GET_FORBIDDEN(HttpStatus.FORBIDDEN.value(), "You are not allowed to access this user's information.", HttpStatus.FORBIDDEN),
  USER_ROLE_NOT_VALID(HttpStatus.BAD_REQUEST.value(), "Invalid user role.", HttpStatus.BAD_REQUEST),
  USER_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "User already exists.", HttpStatus.CONFLICT),
  USER_SIGNUP_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to sign up user.", HttpStatus.INTERNAL_SERVER_ERROR),
  USER_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update user information.", HttpStatus.INTERNAL_SERVER_ERROR),
  USER_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete user.", HttpStatus.INTERNAL_SERVER_ERROR);


  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
