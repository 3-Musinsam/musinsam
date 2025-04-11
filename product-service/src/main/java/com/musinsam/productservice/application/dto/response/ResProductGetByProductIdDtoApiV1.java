package com.musinsam.productservice.application.dto.response;

import com.musinsam.productservice.domain.product.entity.ProductEntity;
import com.musinsam.productservice.domain.product.entity.ProductImageEntity;
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
public class ResProductGetByProductIdDtoApiV1 {

  private Product product;

  public static ResProductGetByProductIdDtoApiV1 of(ProductEntity productEntity,
      ProductImageEntity productImageEntity) {
    return ResProductGetByProductIdDtoApiV1.builder()
        .product(Product.from(productEntity, productImageEntity))
        .build();
  }


  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Product {

    private UUID productId;
    private Shop shop;
    private String name;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Image image;


    public static Product from(ProductEntity productEntity,
        ProductImageEntity productImageEntity) { // +shop 정보
      return Product.builder()
          .productId(null)
          .shop(Shop.from())
          .name(null)
          .price(null)
          .discountPrice(null)
          .image(Image.from(productImageEntity))
          .build();
    }

    @Getter
    @Builder
    public static class Shop {

      private UUID shopId;
      private String shopName;

      public static Shop from() {
        return Shop.builder()
            .shopId(null)
            .shopName(null)
            .build();
      }
    }

    @Getter
    @Builder
    public static class Image {

      private UUID imageId;
      private String imageUrl;

      public static Image from(ProductImageEntity productImageEntity) {
        return Image.builder()
            .imageId(null)
            .imageUrl(null)
            .build();
      }

    }

  }

}
