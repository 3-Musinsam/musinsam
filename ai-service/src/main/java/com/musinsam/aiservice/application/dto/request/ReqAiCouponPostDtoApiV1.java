package com.musinsam.aiservice.application.dto.request;

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
public class ReqAiCouponPostDtoApiV1 {

  private AiCoupon aiCoupon;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AiCoupon {

    private UUID couponId;
    private String couponCode;
    private String discountType;
    private Long discountValue;
    private Long minimumOrderAmount;
    private Long maximumDiscountAmount;
    private ZonedDateTime validFrom;
    private ZonedDateTime validUntil;
    private String status;
  }
}
