package com.musinsam.couponservice.app.domain.vo.couponPolicy;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CouponPolicyErrorCode implements ErrorCode {

  NOT_FOUND_COUPON_POLICY(-1, "Coupon policy not found.", NOT_FOUND),
  INVALID_DATE_RANGE(-1, "Start date must be before end date.", BAD_REQUEST),
  INVALID_DISCOUNT_VALUE(-1, "Invalid discount value for the discount type.", BAD_REQUEST),
  DUPLICATED_COUPON_POLICY(-1, "Coupon policy with the same name already exists.", CONFLICT),
  EXPIRED_COUPON_POLICY(-1, "Coupon policy is expired.", BAD_REQUEST),
  INVALID_COMPANY_ID(-1, "Invalid Company not found.", BAD_REQUEST),
  COUPON_POLICY_ALREADY_DELETED(-1, "Coupon already deleted.", BAD_REQUEST),
  COUPON_POLICY_EXHAUSTED(-1, "Coupon exhausted.", BAD_REQUEST);


  private final Integer code;
  private final String message;
  private final HttpStatus HttpStatus;
}
