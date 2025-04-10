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
public class ResShopGetListDtoApiV1 {

  private Shop shop;

  public static ResShopGetListDtoApiV1 of(ShopEntity shopEntity) {
    return ResShopGetListDtoApiV1.builder()
        .shop(Shop.from(shopEntity))
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
          .id(null)
          .userId(null)
          .name(null)
          .build();
    }

  }

}
