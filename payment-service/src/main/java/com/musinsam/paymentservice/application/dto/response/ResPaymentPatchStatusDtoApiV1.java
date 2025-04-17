package com.musinsam.paymentservice.application.dto.response;

import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPaymentPatchStatusDtoApiV1 {

  private Payment payment;

  public static ResPaymentPatchStatusDtoApiV1 of(PaymentEntity paymentEntity) {
    return ResPaymentPatchStatusDtoApiV1.builder()
        .payment(Payment.from(paymentEntity))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Payment {

    private String status;

    public static Payment from(PaymentEntity paymentEntity) {
      return Payment.builder()
          .status(String.valueOf(paymentEntity.getStatus()))
          .build();
    }
  }
}
