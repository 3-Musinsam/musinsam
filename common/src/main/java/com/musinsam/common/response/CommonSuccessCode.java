package com.musinsam.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommonSuccessCode implements SuccessCode {
  OK(0, "Success");

  private final Integer code;
  private final String message;

}