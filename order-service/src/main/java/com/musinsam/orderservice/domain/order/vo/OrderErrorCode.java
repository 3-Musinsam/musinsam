package com.musinsam.orderservice.domain.order.vo;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {
  ORDER_NOT_FOUND(-1, "Order not found", HttpStatus.BAD_REQUEST),
  ORDER_INVALID_STATUS(-1, "Order has invalid status",
      HttpStatus.BAD_REQUEST),
  ORDER_PRODUCT_NOT_AVAILABLE(-1, "Order product not available",
      HttpStatus.BAD_REQUEST),
  ORDER_ACCESS_DENIED(-1, "No permission to access this order",
      HttpStatus.FORBIDDEN),
  ORDER_ALREADY_CANCELED(-1, "Order already canceled",
      HttpStatus.BAD_REQUEST),
  ORDER_ALREADY_COMPLETED(-1, "Order already completed",
      HttpStatus.BAD_REQUEST),
  ORDER_CANCEL_TIME_EXPIRED(-1, "Order cancel time expired",
      HttpStatus.BAD_REQUEST),
  ORDER_COUPON_NOT_APPLICABLE(-1, "Order coupon cannot be applied",
      HttpStatus.BAD_REQUEST),
  ORDER_CANNOT_BE_CANCELED(-1, "Order cannot be canceled",
      HttpStatus.BAD_REQUEST),
  ORDER_CANNOT_BE_DELETED(-1, "Order cannot be deleted",
      HttpStatus.BAD_REQUEST),
  ORDER_STOCK_RESTORE_FAILED(-1, "Stock Restore request failed",
      HttpStatus.BAD_REQUEST),
  ORDER_LOCK_FAILED(-1, "Order lock failed", HttpStatus.INTERNAL_SERVER_ERROR);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
