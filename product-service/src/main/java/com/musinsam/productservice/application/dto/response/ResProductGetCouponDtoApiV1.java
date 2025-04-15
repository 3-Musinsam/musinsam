package com.musinsam.productservice.application.dto.response;

import com.musinsam.productservice.application.dto.response.ResProductGetCouponDtoApiV1.ProductCouponPage.ProductCoupon;
import com.musinsam.productservice.domain.product.entity.ProductCouponEntity;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResProductGetCouponDtoApiV1 {

  private ProductCouponPage productCouponPage;

  public static ResProductGetCouponDtoApiV1 of(Page<ProductCouponEntity> productCouponEntityPage) {
    return ResProductGetCouponDtoApiV1.builder()
        .productCouponPage(new ProductCouponPage(productCouponEntityPage))
        .build();
  }


  public static class ProductCouponPage extends PagedModel<ProductCoupon> {

    public ProductCouponPage(Page<ProductCouponEntity> productCouponEntityPage) {
      super(
          new PageImpl<>(
              ProductCoupon.from(productCouponEntityPage.getContent()),
              productCouponEntityPage.getPageable(),
              productCouponEntityPage.getTotalElements()
          )
      );
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductCoupon {

      private UUID productId;
      private Coupon coupon;

      public static ProductCoupon from(ProductCouponEntity productCouponEntity) { //받아온 쿠폰 정보
        Coupon coupon = Coupon.builder()
            .couponId(productCouponEntity.getCouponId())
            .couponName(null)
            .endTime(null)
            .build();

        return ProductCoupon.builder()
            .productId(productCouponEntity.getProduct().getId())
            .coupon(coupon)
            .build();
      }

      public static List<ProductCoupon> from(List<ProductCouponEntity> productCouponEntityList) {
        return productCouponEntityList.stream()
            .map(productCouponEntity -> ProductCoupon.from(productCouponEntity))
            .toList();
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

}
