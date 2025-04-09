package com.musinsam.paymentservice.application.dto.response;

import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
import java.math.BigDecimal;
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
public class ResPaymentGetByIdDtoApiV1 {

  private Payment payment;

  public static ResPaymentGetByIdDtoApiV1 of(PaymentEntity paymentEntity) {
    return ResPaymentGetByIdDtoApiV1.builder()
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
    private UUID userId;
    private String paymentStatus;
    private String paymentProvider;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static Payment from(PaymentEntity paymentEntity) {
      return Payment.builder()
          // TODO:
          .build();
    }
  }
}
