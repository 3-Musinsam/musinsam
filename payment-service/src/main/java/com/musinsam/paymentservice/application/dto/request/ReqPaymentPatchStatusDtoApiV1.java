package com.musinsam.paymentservice.application.dto.request;

import com.musinsam.paymentservice.domain.payment.vo.PaymentStatus;
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

  @NotNull
  private PaymentStatus status;
  private String reason;
}
