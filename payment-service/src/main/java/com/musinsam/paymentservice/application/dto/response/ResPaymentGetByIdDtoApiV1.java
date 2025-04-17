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
    private Long userId;
    private String paymentKey;
    private String paymentStatus;
    private String paymentMethod;
    private String reason;
    private BigDecimal totalAmount;
    private ZonedDateTime createdAt;
    private Long createdBy;
    private ZonedDateTime updatedAt;
    private Long updatedBy;

    public static Payment from(PaymentEntity paymentEntity) {
      return Payment.builder()
          .id(paymentEntity.getId())
          .orderId(UUID.fromString(paymentEntity.getProviderOrderId()))
          .userId(paymentEntity.getUserId())
          .paymentKey(paymentEntity.getPaymentKey())
          .paymentMethod(String.valueOf(paymentEntity.getMethodType()))
          .paymentStatus(String.valueOf(paymentEntity.getStatus()))
          .reason(paymentEntity.getReason())
          .totalAmount(paymentEntity.getAmount())
          .createdAt(paymentEntity.getCreatedAt())
          .createdBy(paymentEntity.getCreatedBy())
          .updatedAt(paymentEntity.getUpdatedAt())
          .updatedBy(paymentEntity.getUpdatedBy())
          .build();
    }
  }
}
