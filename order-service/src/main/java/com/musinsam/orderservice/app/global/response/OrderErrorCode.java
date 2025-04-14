package com.musinsam.orderservice.app.global.response;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
  ORDER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "Order not found", HttpStatus.NOT_FOUND),
  ORDER_INVALID_STATUS(HttpStatus.BAD_REQUEST.value(), "Order has invalid status",
      HttpStatus.BAD_REQUEST),
  ORDER_PRODUCT_NOT_AVAILABLE(HttpStatus.BAD_REQUEST.value(), "Order product not available",
      HttpStatus.BAD_REQUEST),
  ORDER_ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "No permission to access this order",
      HttpStatus.FORBIDDEN),
  ORDER_ALREADY_CANCELED(HttpStatus.BAD_REQUEST.value(), "Order already canceled",
      HttpStatus.BAD_REQUEST),
  ORDER_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST.value(), "Order already completed",
      HttpStatus.BAD_REQUEST),
  ORDER_CANCEL_TIME_EXPIRED(HttpStatus.BAD_REQUEST.value(), "Order cancel time expired",
      HttpStatus.BAD_REQUEST),
  ORDER_COUPON_NOT_APPLICABLE(HttpStatus.BAD_REQUEST.value(), "Order coupon cannot be applied",
      HttpStatus.BAD_REQUEST),
  ORDER_CANNOT_BE_CANCELED(HttpStatus.BAD_REQUEST.value(), "Order cannot be canceled",
      HttpStatus.BAD_REQUEST);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
