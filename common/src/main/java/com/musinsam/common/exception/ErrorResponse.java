package com.musinsam.common.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ErrorResponse {
  private final Integer code;
  private final String message;
  private List<FieldError> fieldErrors;

  public ErrorResponse(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
  }

  public ErrorResponse(ErrorCode errorCode, List<FieldError> fieldErrors) {
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
    this.fieldErrors = fieldErrors;
  }

  @Getter
  @AllArgsConstructor
  public static class FieldError {
    private String field;
    private String value;
    private String reason;
  }
}