package com.musinsam.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ApiSuccessCode implements SuccessCode {
  OK(0, "Success", HttpStatus.OK);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;

}
