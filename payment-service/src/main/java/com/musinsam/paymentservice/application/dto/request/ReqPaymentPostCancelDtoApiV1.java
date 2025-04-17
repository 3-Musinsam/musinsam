package com.musinsam.paymentservice.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
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

    @NotNull(message = "취소 금액은 필수입니다.")
    @Positive(message = "취소 금액은 양수여야 합니다.")
    private BigDecimal cancelAmount;
  }
}
