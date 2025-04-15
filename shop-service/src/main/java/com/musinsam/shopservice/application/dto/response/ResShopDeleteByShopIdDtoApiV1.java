package com.musinsam.shopservice.application.dto.response;

import com.musinsam.shopservice.domain.shop.entity.ShopEntity;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResShopDeleteByShopIdDtoApiV1 {

  private Shop shop;

  public static ResShopDeleteByShopIdDtoApiV1 of(ShopEntity shopEntity) {
    return ResShopDeleteByShopIdDtoApiV1.builder()
        .shop(ResShopDeleteByShopIdDtoApiV1.Shop.from(shopEntity))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Shop {

    private UUID id;

    public static Shop from(ShopEntity shopEntity) {
      return Shop.builder()
          .id(shopEntity.getId())
          .build();
    }
  }
}
