package com.musinsam.paymentservice.application.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqPaymentPostInitDtoApiV1 {

  @NotNull(message = "주문 ID는 필수입니다.")
  private UUID orderId;
}
