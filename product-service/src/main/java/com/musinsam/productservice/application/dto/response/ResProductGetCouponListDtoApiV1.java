package com.musinsam.productservice.application.dto.response;

import com.musinsam.productservice.domain.product.entity.ProductCouponEntity;
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
public class ResProductGetCouponListDtoApiV1 {

  private ProductCoupon productCoupon;

  public static ResProductGetCouponListDtoApiV1 of(ProductCouponEntity productCouponEntity,
      String couponName, ZonedDateTime endTime) {
    return ResProductGetCouponListDtoApiV1.builder()
        .productCoupon(ProductCoupon.from(productCouponEntity, couponName, endTime))
        .build();
  }


  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProductCoupon {

    private UUID productId;
    private Coupon coupon;

    public static ProductCoupon from(ProductCouponEntity productCouponEntity, String couponName,
        ZonedDateTime endTime) {
      Coupon coupon = Coupon.builder()
          .couponId(null)
          .couponName(couponName)
          .endTime(endTime)
          .build();

      return ProductCoupon.builder()
          .productId(null)
          .coupon(coupon)
          .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Coupon {

      private UUID couponId;
      private String couponName;
      private ZonedDateTime endTime;
    }
  }


}
