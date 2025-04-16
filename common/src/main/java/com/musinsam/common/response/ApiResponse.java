package com.musinsam.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "message", "data"})
public class ApiResponse<T> {

  private final Integer code;
  private final String message;
  private final T data;

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(ApiSuccessCode.OK.getCode(), ApiSuccessCode.OK.getMessage(), data);
  }

  public static <T> ApiResponse<T> success(String message, T data) {
    return new ApiResponse<>(ApiSuccessCode.OK.getCode(), message, data);
  }

  public static ApiResponse<Void> success(String message) {
    return new ApiResponse<>(ApiSuccessCode.OK.getCode(), message, null);
  }

  public static <T> ApiResponse<T> success(Integer code, String message, T data) {
    return new ApiResponse<>(code, message, data);
  }

  public static <T> ApiResponse<T> success(Integer code, String message) {
    return new ApiResponse<>(code, message, null);
  }

  public static ApiResponse<Void> success() {
    return new ApiResponse<>(ApiSuccessCode.OK.getCode(), ApiSuccessCode.OK.getMessage(), null);
  }

  public static ApiResponse<Void> fail(Integer code, String message) {
    return new ApiResponse<>(code, message, null);
  }

  public static <T> ApiResponse<T> fail(Integer code, String message, T data) {
    return new ApiResponse<>(code, message, data);
  }

  public static ApiResponse<Void> fail(String message) {
    return new ApiResponse<>(ApiSuccessCode.OK.getCode(), message, null);
  }
}
