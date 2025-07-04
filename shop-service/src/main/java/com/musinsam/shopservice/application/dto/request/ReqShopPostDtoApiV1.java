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
public class ReqShopPostDtoApiV1 {

  @Valid
  @NotNull(message = "상점 정보를 입력해주세요.")
  private Shop shop;


  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Shop {

    @NotNull(message = "판매자 아이디를 입력해주세요.")
    private Long userId;

    @NotBlank(message = "상점 이름을 입력해주세요.")
    private String name;

    public ShopEntity toEntity() {
      return ShopEntity.builder()
          .userId(userId)
          .name(name)
          .build();
    }
  }

}
