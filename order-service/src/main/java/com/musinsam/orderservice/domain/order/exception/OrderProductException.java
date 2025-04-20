package com.musinsam.orderservice.domain.order.exception;

import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import lombok.Getter;

@Getter
public class OrderProductException extends OrderException {

    public OrderProductException() {
        super(OrderErrorCode.ORDER_PRODUCT_NOT_AVAILABLE);
    }

    public static OrderProductException notAvailable() {
        return new OrderProductException();
    }
}