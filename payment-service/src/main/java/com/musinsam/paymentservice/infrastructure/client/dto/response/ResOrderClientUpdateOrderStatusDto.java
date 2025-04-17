package com.musinsam.paymentservice.infrastructure.client.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderClientUpdateOrderStatusDto {

  private UUID orderId;
  private String status;
}
