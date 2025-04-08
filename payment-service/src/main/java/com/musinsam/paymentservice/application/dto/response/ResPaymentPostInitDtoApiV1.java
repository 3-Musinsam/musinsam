package com.musinsam.paymentservice.application.dto.response;

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
public class ResPaymentPostInitDtoApiV1 {

  private Payment payment;

  // TODO:
  public static ResPaymentPostInitDtoApiV1 of(UUID paymentId, String paymentKey,
      BigDecimal amount) {
    return ResPaymentPostInitDtoApiV1.builder()
        .payment(Payment.builder()
            .id(paymentId)
            .paymentKey(paymentKey)
            .amount(amount)
            .build())
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Payment {

    private UUID id;
    private String paymentKey;
    private BigDecimal amount;
  }
}
