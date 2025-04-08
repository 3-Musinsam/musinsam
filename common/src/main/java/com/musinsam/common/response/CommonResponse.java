package com.musinsam.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "message", "data"})
public class CommonResponse<T> {

  private final Integer code;
  private final String message;
  private final T data;

  public static <T> CommonResponse<T> success(T data) {
    return new CommonResponse<>(CommonSuccessCode.OK.getCode(), CommonSuccessCode.OK.getMessage(), data);
  }

  public static <T> CommonResponse<T> success(String message, T data) {
    return new CommonResponse<>(CommonSuccessCode.OK.getCode(), message, data);
  }

  public static CommonResponse<Void> success(String message) {
    return new CommonResponse<>(CommonSuccessCode.OK.getCode(), message, null);
  }

  public static <T> CommonResponse<T> success(Integer code, String message, T data) {
    return new CommonResponse<>(code, message, data);
  }

  public static CommonResponse<Void> success() {
    return new CommonResponse<>(CommonSuccessCode.OK.getCode(), CommonSuccessCode.OK.getMessage(), null);
  }

  public static CommonResponse<Void> fail(Integer code, String message) {
    return new CommonResponse<>(code, message, null);
  }

  public static <T> CommonResponse<T> fail(Integer code, String message, T data) {
    return new CommonResponse<>(code, message, data);
  }

  public static CommonResponse<Void> fail(String message) {
    return new CommonResponse<>(CommonSuccessCode.OK.getCode(), message, null);
  }
}
