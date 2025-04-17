package com.musinsam.paymentservice.application.dto.response;

import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
import java.time.ZonedDateTime;
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

  public static ResPaymentPostCancelDtoApiV1 of(PaymentEntity paymentEntity) {
    return ResPaymentPostCancelDtoApiV1.builder()
        .payment(Payment.from(paymentEntity))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Payment {

    private UUID paymentId;
    private String paymentKey;
    private UUID orderId;
    private String status;
    private String cancelReason;
    private ZonedDateTime canceledAt;

    public static Payment from(PaymentEntity paymentEntity) {
      return Payment.builder()
          .paymentId(paymentEntity.getId())
          .paymentKey(paymentEntity.getPaymentKey())
          .orderId(paymentEntity.getOrderId())
          .status(String.valueOf(paymentEntity.getStatus()))
          .canceledAt(ZonedDateTime.now())
          .build();
    }
  }
}
