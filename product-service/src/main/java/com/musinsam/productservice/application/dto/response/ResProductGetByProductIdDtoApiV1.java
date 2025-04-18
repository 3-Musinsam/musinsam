package com.musinsam.productservice.application.dto.response;

import com.musinsam.productservice.domain.product.entity.ProductEntity;
import com.musinsam.productservice.domain.product.entity.ProductImageEntity;
import com.musinsam.productservice.infrastructure.dto.res.ResShopCouponDtoApiV1;
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
public class ResProductGetByProductIdDtoApiV1 {

  private Product product;

  public static ResProductGetByProductIdDtoApiV1 of(ProductEntity productEntity,
      List<ProductImageEntity> productImageEntity, String shopName,
      List<ResShopCouponDtoApiV1.Coupon> couponList) {
    return ResProductGetByProductIdDtoApiV1.builder()
        .product(Product.from(productEntity, productImageEntity, shopName, couponList))
        .build();
  }


  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Product {

    private UUID productId;
    private String name;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Shop shop;
    private List<Image> images;


    public static Product from(ProductEntity productEntity,
        List<ProductImageEntity> productImageEntity, String shopName,
        List<ResShopCouponDtoApiV1.Coupon> couponList) {
      return Product.builder()
          .productId(productEntity.getId())
          .shop(Shop.from(productEntity, shopName, couponList))
          .name(productEntity.getName())
          .price(productEntity.getPrice())
          .discountPrice(productEntity.getDiscountPrice())
          .images(Image.from(productImageEntity))
          .build();
    }

    @Getter
    @Builder
    public static class Shop {

      private UUID shopId;
      private String shopName;
      private List<ResShopCouponDtoApiV1.Coupon> coupons;

      public static Shop from(ProductEntity productEntity, String shopName,
          List<ResShopCouponDtoApiV1.Coupon> couponList) {
        return Shop.builder()
            .shopId(productEntity.getShopId())
            .shopName(shopName)
            .coupons(couponList)
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
            .imageId(productImageEntity.getId())
            .imageUrl(productImageEntity.getImageUrl())
            .build();
      }

      public static List<Image> from(List<ProductImageEntity> productImageEntityList) {
        return productImageEntityList.stream()
            .map(productImageEntity -> Image.from(productImageEntity))
            .toList();
      }

    }

  }

}
