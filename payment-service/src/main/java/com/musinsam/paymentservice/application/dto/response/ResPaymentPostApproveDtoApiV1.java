package com.musinsam.paymentservice.application.dto.response;

import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
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
public class ResPaymentPostApproveDtoApiV1 {

  private Payment payment;

  public static ResPaymentPostApproveDtoApiV1 of(PaymentEntity paymentEntity) {
    return ResPaymentPostApproveDtoApiV1.builder()
        .payment(Payment.from(paymentEntity))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Payment {

    private UUID id;
    private UUID orderId;
    private String paymentStatus;
    private String approvedAt;
    private BigDecimal finalAmount;

    public static Payment from(PaymentEntity paymentEntity) {
      return Payment.builder()
          // TODO:
          .build();
    }
  }
}
