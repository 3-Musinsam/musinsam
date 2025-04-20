package com.musinsam.paymentservice.domain.payment.vo;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {

  PAYMENT_NOT_FOUND(-1, "Payment not found", HttpStatus.NOT_FOUND),
  PAYMENT_INVALID_ORDER_STATUS(-1, "Invalid order status",
      HttpStatus.BAD_REQUEST),
  PAYMENT_UNAUTHORIZED(-1, "Unauthorized payment",
      HttpStatus.UNAUTHORIZED),
  PAYMENT_AMOUNT_MISMATCH(-1, "Amount mismatch",
      HttpStatus.BAD_REQUEST),
  PAYMENT_INVALID_PAYMENT_METHOD(-1, "Invalid payment method",
      HttpStatus.BAD_REQUEST),
  PAYMENT_FAILED(-1, "Payment failed",
      HttpStatus.BAD_REQUEST),
  PAYMENT_INVALID_STATUS(-1, "Invalid status",
      HttpStatus.BAD_REQUEST),
  PAYMENT_CANCEL_TIME_EXPIRED(-1, "Payment cancel time expired",
      HttpStatus.REQUEST_TIMEOUT);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
