package com.musinsam.paymentservice.global.response;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {

  PAYMENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "Payment not found", HttpStatus.NOT_FOUND),
  PAYMENT_INVALID_ORDER_STATUS(HttpStatus.BAD_REQUEST.value(), "Invalid order status",
      HttpStatus.BAD_REQUEST),
  PAYMENT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "Unauthorized payment",
      HttpStatus.UNAUTHORIZED),
  PAYMENT_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST.value(), "Amount mismatch",
      HttpStatus.BAD_REQUEST),
  PAYMENT_INVALID_PAYMENT_METHOD(HttpStatus.BAD_REQUEST.value(), "Invalid payment method",
      HttpStatus.BAD_REQUEST),
  PAYMENT_FAILED(HttpStatus.BAD_REQUEST.value(), "Payment failed",
      HttpStatus.BAD_REQUEST),
  PAYMENT_INVALID_STATUS(HttpStatus.BAD_REQUEST.value(), "Invalid status",
      HttpStatus.BAD_REQUEST);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
