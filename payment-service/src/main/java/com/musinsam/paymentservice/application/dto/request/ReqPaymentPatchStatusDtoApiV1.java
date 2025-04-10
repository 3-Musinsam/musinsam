package com.musinsam.paymentservice.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqPaymentPatchStatusDtoApiV1 {

  @Valid
  @NotNull
  private PaymentStatus paymentStatus;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PaymentStatus {

    private String status;
    private String reason;
  }
}
