package com.musinsam.productservice.application.dto.response;

import com.musinsam.productservice.application.dto.response.ResProductGetCouponDtoApiV1.ProductCouponPage.ProductCoupon;
import com.musinsam.productservice.infrastructure.dto.res.ResShopCouponDtoApiV1;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResProductGetCouponDtoApiV1 {

  private UUID productId;
  private ProductCouponPage productCouponPage;

  public static ResProductGetCouponDtoApiV1 of(List<ResShopCouponDtoApiV1.Coupon> couponList,
      UUID productId, int page, int size) {
    return ResProductGetCouponDtoApiV1.builder()
        .productId(productId)
        .productCouponPage(new ProductCouponPage(couponList, page, size))
        .build();
  }


  public static class ProductCouponPage extends PagedModel<ProductCoupon> {

    public ProductCouponPage(List<ResShopCouponDtoApiV1.Coupon> couponList, int page, int size) {
      super(
          new PageImpl<>(
              ProductCoupon.from(couponList),
              PageRequest.of(page - 1, size),
              couponList.size()
          )
      );
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCoupon {

      private Coupon coupon;

      public static ProductCoupon from(ResShopCouponDtoApiV1.Coupon productCoupon) {
        Coupon coupon = Coupon.builder()
            .couponId(productCoupon.getCouponId())
            .couponName(productCoupon.getCouponName())
            .startTime(productCoupon.getCouponPolicy().getStartTime())
            .endTime(productCoupon.getCouponPolicy().getEndTime())
            .build();

        return ProductCoupon.builder()
            .coupon(coupon)
            .build();
      }

      public static List<ProductCoupon> from(List<ResShopCouponDtoApiV1.Coupon> couponList) {
        return couponList.stream()
            .map(productCoupon -> ProductCoupon.from(productCoupon))
            .toList();
      }


      @Getter
      @Builder
      @NoArgsConstructor
      @AllArgsConstructor
      public static class Coupon {

        private UUID couponId;
        private String couponName;
        private ZonedDateTime startTime;
        private ZonedDateTime endTime;
      }
    }

  }

}
