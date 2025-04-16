package com.musinsam.couponservice.app.domain.vo.coupon;

import static org.springframework.http.HttpStatus.OK;

import com.musinsam.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CouponResponseCode implements SuccessCode {

  COUPON_ISSUE_SUCCESS(0, "Coupon issued successfully.", OK),
  COUPON_CLAIM_SUCCESS(0, "Coupon claimed successfully.", OK),
  COUPON_USE_SUCCESS(0, "Coupon used successfully.", OK),
  COUPONS_GET_SUCCESS(0, "Coupons retrieved successfully.", OK),
  COUPON_GET_SUCCESS(0, "Coupon details retrieved successfully.", OK),
  COUPON_CANCEL_SUCCESS(0, "Coupon usage cancelled successfully.", OK),
  COUPON_DELETE_SUCCESS(0, "Coupon deleted successfully.", OK);

  private final Integer code;
  private final String message;
  private final HttpStatus HttpStatus;
}
