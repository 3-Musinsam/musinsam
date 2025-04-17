package com.musinsam.paymentservice.infrastructure.client.dto.request;

import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostApproveDtoApiV1;
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
public class ReqTossPaymentApproveDto {

  private String paymentKey;
  private UUID orderId;
  private BigDecimal amount;

  public static ReqTossPaymentApproveDto of(ReqPaymentPostApproveDtoApiV1 requestDto) {
    return ReqTossPaymentApproveDto.builder()
        .paymentKey(requestDto.getPaymentApproval().getPaymentKey())
        .orderId(UUID.fromString(requestDto.getPaymentApproval().getOrderId()))
        .amount(requestDto.getPaymentApproval().getAmount())
        .build();
  }
}
