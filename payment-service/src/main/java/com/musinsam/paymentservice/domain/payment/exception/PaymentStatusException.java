package com.musinsam.paymentservice.domain.payment.exception;

import com.musinsam.paymentservice.domain.payment.vo.PaymentErrorCode;
import lombok.Getter;

@Getter
public class PaymentStatusException extends PaymentException {

  public PaymentStatusException(PaymentErrorCode errorCode) {
    super(errorCode);
  }

  public static PaymentStatusException invalidStatus() {
    return new PaymentStatusException(PaymentErrorCode.PAYMENT_INVALID_STATUS);
  }

  public static PaymentStatusException invalidOrderStatus() {
    return new PaymentStatusException(PaymentErrorCode.PAYMENT_INVALID_ORDER_STATUS);
  }
}
