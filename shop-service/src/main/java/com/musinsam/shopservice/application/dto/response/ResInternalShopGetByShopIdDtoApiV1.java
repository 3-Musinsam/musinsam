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
public class ResInternalShopGetByShopIdDtoApiV1 {

  private Shop shop;

  public static ResInternalShopGetByShopIdDtoApiV1 of(ShopEntity shopEntity) {
    return ResInternalShopGetByShopIdDtoApiV1.builder()
        .shop(ResInternalShopGetByShopIdDtoApiV1.Shop.from(shopEntity))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Shop {

    private UUID id;
    private Long userId;
    private String name;

    public static Shop from(ShopEntity shopEntity) {
      return Shop.builder()
          .id(shopEntity.getId())
          .userId(shopEntity.getUserId())
          .name(shopEntity.getName())
          .build();
    }
  }
}
