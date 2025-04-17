package com.musinsam.paymentservice.infrastructure.client.dto.request;

import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
import com.musinsam.paymentservice.infrastructure.client.dto.response.ResTossPaymentApproveDto.Failure;
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
public class ReqOrderClientUpdateOrderStatusDto {

  private UUID orderId;
  private String status;
  private String code;
  private String reason;
  private BigDecimal amount;

  public static ReqOrderClientUpdateOrderStatusDto cancelOf(PaymentEntity paymentEntity) {
    return ReqOrderClientUpdateOrderStatusDto.builder()
        .orderId(paymentEntity.getOrderId())
        .status("CANCELED")
        .reason(paymentEntity.getReason())
        .build();
  }

  public static ReqOrderClientUpdateOrderStatusDto successOf(UUID orderId) {
    return ReqOrderClientUpdateOrderStatusDto.builder()
        .orderId(orderId)
        .status("PAID")
        .reason("Payment Successful")
        .build();
  }

  public static ReqOrderClientUpdateOrderStatusDto failureWithTossResponse(UUID orderId,
      Failure failure) {
    return ReqOrderClientUpdateOrderStatusDto.builder()
        .orderId(orderId)
        .status("FAILED")
        .code(failure.getCode())
        .reason(failure.getMessage())
        .build();
  }
}
