package com.musinsam.productservice.application.dto.response;

import java.math.BigDecimal;
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
public class ResProductGetDtoApiV1 {

  private Product product;


  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Product {

    private UUID productId;
    private UUID shopId;
    private String shopName;
    private String name;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private List<UUID> images;

  }

}
