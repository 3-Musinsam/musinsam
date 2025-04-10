package com.musinsam.alarmservice.infrastructure.exception;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum AlarmErrorCode implements ErrorCode {
  INVALID_INPUT(-1, "Invalid input value.", HttpStatus.BAD_REQUEST),
  UNAUTHORIZED(-1, "Unauthorized access. Login required.", HttpStatus.UNAUTHORIZED),
  FORBIDDEN(-1, "You do not have permission to access this resource.", HttpStatus.FORBIDDEN),
  ALARM_NOT_FOUND(-1, "The requested resource was not found.", HttpStatus.NOT_FOUND),
  INTERNAL_ERROR(-1, "An internal server error has occurred.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
