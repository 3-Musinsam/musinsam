package com.musinsam.productservice.infrastructure.dto.res;

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
public class ResShopCouponDto {

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
