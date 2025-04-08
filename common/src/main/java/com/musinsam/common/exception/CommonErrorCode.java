package com.musinsam.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommonErrorCode implements ErrorCode {
  INVALID_INPUT("COMMON_001", "Invalid input value.", HttpStatus.BAD_REQUEST),
  UNAUTHORIZED("COMMON_002", "Unauthorized access. Login required.", HttpStatus.UNAUTHORIZED),
  FORBIDDEN("COMMON_003", "You do not have permission to access this resource.", HttpStatus.FORBIDDEN),
  NOT_FOUND("COMMON_004", "The requested resource was not found.", HttpStatus.NOT_FOUND),
  INTERNAL_ERROR("COMMON_999", "An internal server error has occurred.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final String code;
  private final String message;
  private final HttpStatus httpStatus;
}