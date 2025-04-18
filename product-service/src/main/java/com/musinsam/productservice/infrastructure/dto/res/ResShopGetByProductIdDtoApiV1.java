package com.musinsam.productservice.infrastructure.dto.res;

import java.util.UUID;
import lombok.Getter;

@Getter
public class ResShopGetByProductIdDtoApiV1 {

  private Shop shop;

  @Getter
  public static class Shop {

    private UUID id;
    private Long userId;
    private String name;
  }

}
