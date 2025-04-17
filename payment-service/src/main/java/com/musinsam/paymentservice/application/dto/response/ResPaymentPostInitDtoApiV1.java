package com.musinsam.paymentservice.application.dto.response;

import com.musinsam.paymentservice.infrastructure.client.dto.response.ResOrderClientGetByOrderIdDto;
import com.musinsam.paymentservice.infrastructure.config.PaymentConfig;
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

  public static ResPaymentPostInitDtoApiV1 of(ResOrderClientGetByOrderIdDto clientGetByOrderIdDto,
      PaymentConfig paymentConfig) {
    return ResPaymentPostInitDtoApiV1.builder()
        .payment(Payment.from(clientGetByOrderIdDto, paymentConfig))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Payment {

    private UUID orderId;
    private String orderName;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String clientKey;
    private String customerKey;
    private String successUrl;
    private String failureUrl;

    public static Payment from(ResOrderClientGetByOrderIdDto resDto,
        PaymentConfig config) {
      return Payment.builder()
          .orderId(resDto.getOrder().getId())
          .orderName(resDto.getOrder().getOrderName())
          .discountAmount(resDto.getOrder().getDiscountAmount())
          .finalAmount(resDto.getOrder().getFinalAmount())
          .clientKey(config.getClientKey())
          .customerKey(config.generatedCustomerKey(resDto.getOrder().getUserId()))
          .successUrl(config.getSuccessRedirectUrl())
          .failureUrl(config.getFailRedirectUrl())
          .build();
    }
  }
}
