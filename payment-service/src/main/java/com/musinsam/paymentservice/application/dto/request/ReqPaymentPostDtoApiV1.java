package com.musinsam.paymentservice.application.dto.request;

import jakarta.validation.Valid;
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
public class ReqPaymentPostDtoApiV1 {

  @Valid
  private Payment payment;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Payment {

    private UUID orderId;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String paymentProvider;
  }
}
