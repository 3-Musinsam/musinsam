package com.musinsam.couponservice.app.application.dto.v1.coupon.request;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqCouponValidateDtoApiV1 {
  private Long userId;
  private UUID companyId;
  private BigDecimal totalAmount;
}