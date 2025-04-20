package com.musinsam.paymentservice.domain.payment.exception;


import com.musinsam.common.exception.CustomException;
import com.musinsam.common.exception.ErrorCode;
import com.musinsam.paymentservice.domain.payment.vo.PaymentErrorCode;
import lombok.Getter;

@Getter
public class PaymentException extends CustomException {

  public PaymentException(ErrorCode errorCode) {
    super(errorCode);
  }

  public static PaymentException from(PaymentErrorCode errorCode) {
    return new PaymentException(errorCode);
  }
}