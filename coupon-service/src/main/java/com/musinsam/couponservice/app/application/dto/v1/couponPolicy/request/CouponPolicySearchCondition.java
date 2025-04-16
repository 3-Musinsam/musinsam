package com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request;

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
public class CouponPolicySearchCondition {
  private UUID couponPolicyId;
  private String couponPolicyName;
  private String couponPolicyDescription;
  private DiscountType discountType;
  private UUID companyId;
  private ZonedDateTime startedFrom;
  private ZonedDateTime startedTo;
  private ZonedDateTime endedFrom;
  private ZonedDateTime endedTo;
  private ZonedDateTime createdFrom;
  private ZonedDateTime createdTo;
}
