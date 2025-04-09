package com.musinsam.productservice.application.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResProductGetStockDtoApiV1 {

  private Stock stock;


  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Stock {

    private UUID productId;
    private Integer stock;

  }

}
