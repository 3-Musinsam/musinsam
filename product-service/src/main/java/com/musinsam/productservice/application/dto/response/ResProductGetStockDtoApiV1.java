package com.musinsam.productservice.application.dto.response;

import com.musinsam.productservice.domain.product.entity.ProductEntity;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResProductGetStockDtoApiV1 {

  private Stock stock;

  public static ResProductGetStockDtoApiV1 of(ProductEntity productEntity) {
    return ResProductGetStockDtoApiV1.builder()
        .stock(Stock.from(productEntity))
        .build();
  }


  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Stock {

    private UUID productId;
    private Integer stock;

    public static Stock from(ProductEntity productEntity) {
      return Stock.builder()
          .productId(null)
          .stock(null)
          .build();
    }

  }

}
