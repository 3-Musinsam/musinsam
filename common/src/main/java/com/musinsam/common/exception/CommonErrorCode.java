package com.musinsam.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CommonErrorCode implements ErrorCode {
  INVALID_INPUT(-1, "Invalid input value.", HttpStatus.BAD_REQUEST),
  UNAUTHORIZED(-1, "Unauthorized access. Login required.", HttpStatus.UNAUTHORIZED),
  FORBIDDEN(-1, "You do not have permission to access this resource.", HttpStatus.FORBIDDEN),
  NOT_FOUND(-1, "The requested resource was not found.", HttpStatus.NOT_FOUND),
  INTERNAL_ERROR(-1, "An internal server error has occurred.", HttpStatus.INTERNAL_SERVER_ERROR),
  FEIGN_CLIENT_ERROR(-1, "Feign client error has occurred.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
