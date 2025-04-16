package com.musinsam.couponservice.app.application.dto.v1.coupon.request;

import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqCouponIssueDtoApiV1 {
  private Long userId;
  private UUID couponPolicyId;
  private String couponCode;
}
