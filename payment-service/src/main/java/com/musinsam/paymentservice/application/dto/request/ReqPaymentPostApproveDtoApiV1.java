package com.musinsam.paymentservice.application.dto.request;

import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
import com.musinsam.paymentservice.domain.payment.vo.PaymentMethod;
import com.musinsam.paymentservice.domain.payment.vo.PaymentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "결제 키는 필수입니다.")
    private String paymentKey;

    @NotBlank(message = "주문 ID는 필수입니다.")
    private String orderId;

    @NotNull(message = "결제 금액은 필수입니다.")
    @Positive(message = "결제 금액은 양수여야 합니다.")
    private BigDecimal amount;

    public PaymentEntity toPaymentEntity(Long userId, PaymentMethod paymentMethod,
        PaymentStatus paymentStatus) {
      return PaymentEntity.builder()
          .userId(userId)
          .amount(amount)
          .paymentKey(paymentKey)
          .providerOrderId(orderId)
          .methodType(paymentMethod)
          .status(paymentStatus)
          .build();
    }
  }
}
