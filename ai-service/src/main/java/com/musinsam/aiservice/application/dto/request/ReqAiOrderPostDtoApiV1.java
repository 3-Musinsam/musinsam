package com.musinsam.aiservice.application.dto.request;

import java.text.DecimalFormat;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqAiOrderPostDtoApiV1 {

  private AiOrder aiOrder;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AiOrder {

    private UUID orderId;
    private String orderName;
    private String receiverName;
    private String address;
    private String productName;
    private Integer quantity;
    private DecimalFormat totalAmount;
  }
}
