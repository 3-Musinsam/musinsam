package com.musinsam.productservice.application.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResProductGetListDtoApiV1 {

  private ProductList productList;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProductList {

    private String name;
    private String shopName;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private UUID imageId;

  }

}
