package com.musinsam.productservice.application.dto.response;

import com.musinsam.productservice.domain.product.entity.ProductEntity;
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

  private Product product;

  public static ResProductGetListDtoApiV1 of(ProductEntity productEntity, String shopName,
      UUID imageId) {
    return ResProductGetListDtoApiV1.builder()
        .product(Product.from(productEntity, shopName, imageId))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Product {

    private String name;
    private String shopName;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private UUID imageId;

    public static Product from(ProductEntity productEntity, String shopName, UUID imageId) {
      return Product.builder()
          .name(null)
          .shopName(shopName)
          .price(null)
          .discountPrice(null)
          .imageId(imageId)
          .build();
    }

  }

}
