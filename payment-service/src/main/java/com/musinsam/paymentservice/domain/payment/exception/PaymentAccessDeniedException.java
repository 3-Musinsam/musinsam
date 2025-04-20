package com.musinsam.paymentservice.domain.payment.exception;

import com.musinsam.paymentservice.domain.payment.vo.PaymentErrorCode;
import lombok.Getter;

@Getter
public class PaymentAccessDeniedException extends PaymentException {

  public PaymentAccessDeniedException() {
    super(PaymentErrorCode.PAYMENT_UNAUTHORIZED);
  }

  public static PaymentAccessDeniedException create() {
    return new PaymentAccessDeniedException();
  }
}
