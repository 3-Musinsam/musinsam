package com.musinsam.paymentservice.domain.payment.exception;

import com.musinsam.paymentservice.domain.payment.vo.PaymentErrorCode;
import lombok.Getter;

@Getter
public class PaymentNotFoundException extends PaymentException {

  public PaymentNotFoundException() {
    super(PaymentErrorCode.PAYMENT_NOT_FOUND);
  }

  public static PaymentNotFoundException create() {
    return new PaymentNotFoundException();
  }
}
