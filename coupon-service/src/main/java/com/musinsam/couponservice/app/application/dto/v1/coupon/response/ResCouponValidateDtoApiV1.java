package com.musinsam.couponservice.app.application.dto.v1.coupon.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResCouponValidateDtoApiV1 {
  private UUID couponId;
  private Integer discountAmount;
  private BigDecimal finalAmount;
}