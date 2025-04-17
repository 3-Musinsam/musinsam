package com.musinsam.orderservice.infrastructure.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReqOrderClientUpdateOrderStatusDto {

  private String orderId;
  private String status;
  private String reason;
}
