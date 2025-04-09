package com.musinsam.paymentservice.application.dto.request;

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
