package com.musinsam.orderservice.domain.order.exception;

import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import lombok.Getter;

@Getter
public class OrderControlException extends OrderException {

  public OrderControlException(OrderErrorCode errorCode) {
    super(errorCode);
  }

  public static OrderControlException cannotBeCanceled() {
    return new OrderControlException(OrderErrorCode.ORDER_CANNOT_BE_CANCELED);
  }

  public static OrderControlException cannotBeDeleted() {
    return new OrderControlException(OrderErrorCode.ORDER_CANNOT_BE_DELETED);
  }

  public static OrderControlException cancelTimeExpired() {
    return new OrderControlException(OrderErrorCode.ORDER_CANCEL_TIME_EXPIRED);
  }
}