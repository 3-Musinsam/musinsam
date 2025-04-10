package com.musinsam.paymentservice.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqPaymentPostApproveDtoApiV1 {

  @Valid
  @NotNull
  private PaymentApproval paymentApproval;

  /**
   * 결제 승인 정보에 대한 정보 입니다.
   */
  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PaymentApproval {

    private String paymentKey;
    private String orderId;
    private BigDecimal amount;
    private String responseCode;
    private String responseMessage;
    private String metadata;
  }
}
