package com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request;

import com.musinsam.couponservice.app.doamin.vo.DiscountType;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class ReqCouponPolicyIssueDtoApiV1 {

  private CouponPolicy couponPolicy;

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class CouponPolicy {
    private String name;
    private String description;
    private DiscountType discountType;
    private Integer discountValue;
    private Integer minimumOrderAmount;
    private Integer maximumDiscountAmount;
    private Integer totalQuantity;
    private ZonedDateTime startedAt;
    private ZonedDateTime endedAt;
    private UUID companyId;
  }
}