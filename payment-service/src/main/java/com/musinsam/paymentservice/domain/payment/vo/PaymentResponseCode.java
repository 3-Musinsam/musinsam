package com.musinsam.paymentservice.domain.payment.vo;

import static org.springframework.http.HttpStatus.OK;

import com.musinsam.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentResponseCode implements SuccessCode {
  PAYMENT_INIT_SUCCESS(0, "Payment initiated successfully.", OK),
  PAYMENT_APPROVE_SUCCESS(0, "Payment approved successfully.", OK),
  PAYMENT_GET_SUCCESS(0, "Payment retrieved successfully.", OK),
  PAYMENT_LIST_GET_SUCCESS(0, "Payment list retrieved successfully.", OK),
  PAYMENT_CANCEL_SUCCESS(0, "Payment canceled successfully.", OK),
  ;

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
