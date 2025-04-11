package com.musinsam.shopservice.application.dto.request;

import com.musinsam.shopservice.domain.shop.entity.ShopEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqShopPutByShopIdDtoApiV1 {

  @Valid
  @NotNull(message = "수정할 상점 정보를 입력해주세요.")
  private Shop shop;


  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Shop {

    @NotBlank(message = "상점 이름을 입력해주세요.")
    private String name;

    public void updateOf(ShopEntity shopEntity) {
      //shopEntity.setName(name);
    }
  }

}
