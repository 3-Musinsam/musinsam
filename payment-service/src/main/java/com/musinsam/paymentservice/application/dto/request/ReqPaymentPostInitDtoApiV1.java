package com.musinsam.paymentservice.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
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

  @Valid
  @NotNull
  private Payment payment;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Payment {

    private UUID orderId;
    private BigDecimal amount;
    private String paymentProvider;
  }
}
