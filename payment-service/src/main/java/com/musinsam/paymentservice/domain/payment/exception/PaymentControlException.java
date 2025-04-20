package com.musinsam.paymentservice.domain.payment.exception;

import com.musinsam.paymentservice.domain.payment.vo.PaymentErrorCode;
import lombok.Getter;

@Getter
public class PaymentControlException extends PaymentException {

  public PaymentControlException(PaymentErrorCode errorCode) {
    super(errorCode);
  }

  public static PaymentControlException cancelTimeExpired() {
    return new PaymentControlException(PaymentErrorCode.PAYMENT_CANCEL_TIME_EXPIRED);
  }
}
