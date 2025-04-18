package com.musinsam.shopservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReqShopGetSearchDtoApiV1 {

  @Getter
  @Builder
  public static class ShopDto {

    @JsonProperty("id")
    private UUID id;

    private Long userId;

    private String name;

  }
}
