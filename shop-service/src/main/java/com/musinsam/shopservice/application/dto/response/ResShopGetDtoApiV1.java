package com.musinsam.shopservice.application.dto.response;

import com.musinsam.shopservice.application.dto.response.ResShopGetDtoApiV1.ShopPage.Shop;
import com.musinsam.shopservice.domain.shop.entity.ShopEntity;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResShopGetDtoApiV1 {

  private ShopPage shopPage;


  public static ResShopGetDtoApiV1 of(Page<ShopEntity> shopEntityPage) {
    return ResShopGetDtoApiV1.builder()
        .shopPage(new ShopPage(shopEntityPage))
        .build();
  }

  @Getter
  @ToString
  public static class ShopPage extends PagedModel<Shop> {

    public ShopPage(Page<ShopEntity> shopEntityPage) {
      super(
          new PageImpl<>(
              Shop.from(shopEntityPage.getContent()),
              shopEntityPage.getPageable(),
              shopEntityPage.getTotalElements()
          )
      );
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Shop {

      private UUID id;
      private Long userId;
      private String name;

      public static List<Shop> from(List<ShopEntity> shopEntityList) {
        return shopEntityList.stream()
            .map(Shop::from)
            .toList();
      }

      public static Shop from(ShopEntity shopEntity) {
        return Shop.builder()
            .id(shopEntity.getId())
            .userId(shopEntity.getUserId())
            .name(shopEntity.getName())
            .build();
      }
    }
  }
}
