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
public class ResPaymentPostCancelDtoApiV1 {

  private Payment payment;

  public static ResPaymentPostCancelDtoApiV1 of() {
    return ResPaymentPostCancelDtoApiV1.builder()
        .payment(Payment.builder()
            // TODO:
            .build())
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Payment {

    private UUID id;
    private String paymentStatus;
    private BigDecimal canceledAmount;
    private String cancelReason;
    private String canceledAt;
  }
}
