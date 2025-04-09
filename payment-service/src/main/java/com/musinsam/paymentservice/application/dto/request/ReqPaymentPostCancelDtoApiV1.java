package com.musinsam.paymentservice.application.dto.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqPaymentPostCancelDtoApiV1 {

  @Valid
  private PaymentCancel paymentCancel;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PaymentCancel {

    private String cancelReason;
  }
}
