package com.musinsam.shopservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReqShopGetSearchDtoApiV1 {

  @Valid
  @NotNull(message = "상점 정보를 입력해주세요.")
  private Shop shop;

  @Getter
  @Builder
  public static class Shop {

    @JsonProperty("id")
    private UUID id;

    private Long userId;

    private String name;

  }
}
