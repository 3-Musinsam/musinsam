package com.musinsam.productservice.application.dto.request;

import com.musinsam.productservice.domain.product.entity.ProductEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqProductPatchByProductIdDtoApiV1 {

  @Valid
  @NotNull(message = "수정할 상품 정보를 입력해주세요.")
  private Product product;

  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Product {

    @NotNull(message = "수정할 재고 수량을 입력해주세요.")
    @PositiveOrZero(message = "재고 수량은 0개 이상이어야 합니다.")
    private Integer stock;

    public void updateOf(ProductEntity productEntity) {
      //productEntity.setStock(stock);
    }

  }

}
