package com.musinsam.shopservice.application.dto.response;

import com.musinsam.shopservice.application.dto.response.ResInternalShopGetDtoApiV1.ShopPage.Shop;
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
public class ResInternalShopGetDtoApiV1 {

  private ShopPage shopPage;

  /**
   * Creates a new response DTO containing a paginated list of shops from the given page of shop entities.
   *
   * @param shopEntityPage the page of shop entities to convert
   * @return a response DTO wrapping the paginated shop data
   */
  public static ResInternalShopGetDtoApiV1 of(Page<ShopEntity> shopEntityPage) {
    return ResInternalShopGetDtoApiV1.builder()
        .shopPage(new ShopPage(shopEntityPage))
        .build();
  }

  @Getter
  @ToString
  public static class ShopPage extends PagedModel<Shop> {

    /**
     * Constructs a paginated model of shop DTOs from a page of shop entities.
     *
     * Converts the content of the provided {@code Page<ShopEntity>} into a list of {@code Shop} DTOs,
     * preserving pagination and metadata for API responses.
     *
     * @param shopEntityPage the page of shop entities to convert
     */
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

      /**
       * Converts a list of ShopEntity objects into a list of Shop DTOs.
       *
       * @param shopEntityList the list of ShopEntity instances to convert
       * @return a list of Shop DTOs corresponding to the input entities
       */
      public static List<Shop> from(List<ShopEntity> shopEntityList) {
        return shopEntityList.stream()
            .map(Shop::from)
            .toList();
      }

      /**
       * Converts a ShopEntity instance to a Shop DTO.
       *
       * @param shopEntity the ShopEntity to convert
       * @return a Shop DTO with fields populated from the given entity
       */
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
