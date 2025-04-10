package com.musinsam.couponservice.app.doamin.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CouponStatus {
  AVAILABLE("발급 가능한 쿠폰"),
  ISSUED("발급된 쿠폰"),
  USED("사용된 쿠폰"),
  EXPIRED("만료된 쿠폰"),
  CANCELLED("취소된 쿠폰");

  private final String description;
}
