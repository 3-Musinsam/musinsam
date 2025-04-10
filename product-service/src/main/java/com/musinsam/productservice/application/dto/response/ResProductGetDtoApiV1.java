package com.musinsam.productservice.application.dto.response;

import com.musinsam.productservice.domain.product.entity.ProductEntity;
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

  public static ResProductGetDtoApiV1 of(ProductEntity productEntity, String shopName,
      List<UUID> images) {
    return ResProductGetDtoApiV1.builder()
        .product(Product.from(productEntity, shopName, images))
        .build();
  }


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


    public static Product from(ProductEntity productEntity, String shopName, List<UUID> images) {
      return Product.builder()
          .productId(null)
          .shopId(null)
          .shopName(shopName)
          .name(null)
          .price(null)
          .discountPrice(null)
          .images(images)
          .build();
    }

  }

}
