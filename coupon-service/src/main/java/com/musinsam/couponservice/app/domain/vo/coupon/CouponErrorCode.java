package com.musinsam.couponservice.app.domain.vo.coupon;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponErrorCode implements ErrorCode {

  NOT_FOUND_COUPON(0, "Coupon not found.", NOT_FOUND),
  COUPON_EXPIRED(0, "Coupon has expired.", BAD_REQUEST),
  COUPON_ALREADY_USED(0, "Coupon has already been used.", BAD_REQUEST),
  COUPON_ISSUE_LIMIT_EXCEEDED(0, "Coupon issue limit exceeded.", BAD_REQUEST),
  INVALID_COUPON_CONDITION(0, "Invalid coupon usage conditions.", BAD_REQUEST);

  private final Integer code;
  private final String message;
  private final org.springframework.http.HttpStatus HttpStatus;
}
