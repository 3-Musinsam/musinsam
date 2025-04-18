package com.musinsam.shopservice.application.dto.response;

import java.time.ZonedDateTime;
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
public class ResShopCouponDtoApiV1 {

  private List<Coupon> couponList;

  @Builder
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Coupon {

    private UUID couponId;
    private String couponName;
    private CouponPolicy couponPolicy;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CouponPolicy {

      private ZonedDateTime startTime;
      private ZonedDateTime endTime;
    }
  }
}