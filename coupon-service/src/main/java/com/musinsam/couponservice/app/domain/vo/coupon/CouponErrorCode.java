package com.musinsam.couponservice.app.domain.vo.coupon;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponErrorCode implements ErrorCode {

  NOT_FOUND_COUPON(-1, "Coupon not found.", NOT_FOUND),
  COUPON_EXPIRED(-1, "Coupon has expired.", BAD_REQUEST),
  COUPON_ALREADY_USED(-1, "Coupon has already been used.", BAD_REQUEST),
  COUPON_ISSUE_LIMIT_EXCEEDED(-1, "Coupon issue limit exceeded.", BAD_REQUEST),
  INVALID_COUPON_CONDITION(-1, "Invalid coupon usage conditions.", BAD_REQUEST),
  COUPON_CODE_ALREADY_EXISTS(-1, "Coupon code already exists.", BAD_REQUEST),
  INVALID_COUPON_DATE(-1, "Invalid coupon usage date", BAD_REQUEST),
  INVALID_COUPON_COMPANY_ID(-1, "Invalid coupon company id.", BAD_REQUEST),
  INVALID_COUPON_MINIMUM_ORDER(-1, "Invalid coupon usage minimum order.", BAD_REQUEST),
  COUPON_DOSE_NOT_BELONG_TO_USER(-1, "Coupon dose not belong to user.", BAD_REQUEST),
  COUPON_ALREADY_DELETED(-1, "Coupon already deleted.", BAD_REQUEST),
  COUPON_ALREADY_ISSUED_BY_USER(-1, "Coupon already issued by user.", BAD_REQUEST),
  COUPON_ALREADY_CLAIMED(-1, "Coupon already claimed.", BAD_REQUEST),
  COUPON_NOT_USED(-1, "Coupon is not in a state eligible for restoration.", BAD_REQUEST),
  COUPONS_ALL_USED_UP(-1, "All coupons have been used up.", BAD_REQUEST),
  COUPON_ISSUE_TRY_AGAIN(-1, "High volume of coupon issuance requests. Please try again later.", BAD_REQUEST),
  COUPON_ERROR_UPDATE_STATE(-1, "Error updating coupon state", BAD_REQUEST),
  COUPON_NOT_FOUND(-1, "Coupon could not be found.", BAD_REQUEST);

  private final Integer code;
  private final String message;
  private final org.springframework.http.HttpStatus HttpStatus;
}
