package com.musinsam.couponservice.app.application.dto.v1.coupon.request;

import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponSearchCondition {
  UUID couponId;
  UUID couponPolicyId;
  Long userId;
  UUID orderId;
  String couponCode;
  CouponStatus couponStatus;
  String policyName;
  DiscountType discountType;
  UUID companyId;
  ZonedDateTime usedFrom;
  ZonedDateTime usedTo;
  ZonedDateTime createdFrom;
  ZonedDateTime createdTo;
}
