package com.musinsam.orderservice.domain.order.exception;

import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import lombok.Getter;

@Getter
public class OrderStockRestoreException extends OrderException {

  public OrderStockRestoreException() {
    super(OrderErrorCode.ORDER_STOCK_RESTORE_FAILED);
  }
}
