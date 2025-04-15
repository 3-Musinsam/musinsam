package com.musinsam.couponservice.app.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiscountType {
  FIXED_AMOUNT("정액"),
  PERCENTAGE("정률");

  private final String description;
}
