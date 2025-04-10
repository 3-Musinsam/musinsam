package com.musinsam.productservice.application.dto.request;

import com.musinsam.productservice.domain.product.entity.ProductCouponEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqProductPostApplyCouponDtoApiV1 {

  @Valid
  @NotNull(message = "쿠폰 정보를 입력해주세요.")
  private ProductCoupon productCoupon;


  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProductCoupon {

    @NotNull(message = "쿠폰 Id를 입력해주세요.")
    private UUID couponId;

    public static ProductCouponEntity toEntityWith(UUID productId) {
      return ProductCouponEntity.builder()
          .build();
    }
  }

}
