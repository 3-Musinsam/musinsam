package com.musinsam.couponservice.app.application.dto.v2.coupon.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqCouponIssueDtoApiV2 {
  private UUID couponPolicyId;
}
