package com.musinsam.couponservice.app.application.dto.v1.coupon.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqAvailableCouponDtoApiV1 {
  private Long userId;
  private List<UUID> companyIds;
  private BigDecimal totalAmount;
}
