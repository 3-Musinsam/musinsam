package com.musinsam.paymentservice.application.dto.response;

import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
import com.musinsam.paymentservice.domain.payment.vo.PaymentStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPaymentPostApproveDtoApiV1 {

  private Payment payment;

  public static ResPaymentPostApproveDtoApiV1 of(PaymentEntity paymentEntity) {
    return ResPaymentPostApproveDtoApiV1.builder()
        .payment(Payment.from(paymentEntity))
        .build();
  }

  /**
   * @See <a href="https://docs.tosspayments.com/reference#payment-%EA%B0%9D%EC%B2%B4">Toss Payment
   * 객체
   * </a>
   */
  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Payment {

    private String mId;
    private String type;
    private String orderId;
    private String tossOrderId; // toss에 저장되는 orderId
    private String method;
    private BigDecimal totalAmount;
    private PaymentStatus status;
    private String paymentKey;
    private String lastTransactionKey;
    private String secret;

    private Cancels cancels;
    private Card card;
    private Failure failure;

    public static Payment from(PaymentEntity paymentEntity) {
      return Payment.builder()
          .tossOrderId(paymentEntity.getProviderOrderId())
          .method(String.valueOf(paymentEntity.getMethodType()))
          .totalAmount(paymentEntity.getAmount())
          .status(paymentEntity.getStatus())
          .paymentKey(paymentEntity.getPaymentKey())
          .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Cancels {

      private String receiptKey;
      private String cancelRequestId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Card {

      private BigDecimal amount;
      private String acquirerCode;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Failure {

      private String code;
      private String message;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Discount {

      private Integer amount;
    }
  }
}
