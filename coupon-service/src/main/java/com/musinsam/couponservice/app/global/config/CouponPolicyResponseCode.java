package com.musinsam.couponservice.app.global.config;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.musinsam.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CouponPolicyResponseCode implements SuccessCode {

  COUPON_POLICY_ISSUE_SUCCESS(0, "Coupon policy issued successfully.", CREATED),
  COUPON_POLICY_GET_SUCCESS(0, "Coupon policy retrieved successfully.", OK),
  COUPON_POLICIES_GET_SUCCESS(0, "Coupon policies retrieved successfully.", OK),
  COUPON_POLICY_DELETE_SUCCESS(0, "Coupon policy deleted successfully.", NO_CONTENT);


  private final Integer code;
  private final String message;
  private final HttpStatus HttpStatus;
}
