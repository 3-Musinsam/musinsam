package com.musinsam.orderservice.domain.order.exception;

import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import lombok.Getter;

@Getter
public class OrderAccessDeniedException extends OrderException {

  public OrderAccessDeniedException() {
    super(OrderErrorCode.ORDER_ACCESS_DENIED);
  }

  public static OrderAccessDeniedException create() {
    return new OrderAccessDeniedException();
  }
}