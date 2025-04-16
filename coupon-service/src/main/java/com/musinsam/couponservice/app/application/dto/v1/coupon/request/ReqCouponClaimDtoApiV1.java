package com.musinsam.couponservice.app.application.dto.v1.coupon.request;


import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqCouponClaimDtoApiV1 {
  private UUID couponId;
}
