package com.musinsam.orderservice.domain.order.exception;

import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import lombok.Getter;

@Getter
public class OrderNotFoundException extends OrderException {

    public OrderNotFoundException() {
        super(OrderErrorCode.ORDER_NOT_FOUND);
    }

    public static OrderNotFoundException create() {
        return new OrderNotFoundException();
    }
}