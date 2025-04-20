package com.musinsam.orderservice.domain.order.exception;

import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import lombok.Getter;

@Getter
public class OrderStatusException extends OrderException {

    public OrderStatusException(OrderErrorCode errorCode) {
        super(errorCode);
    }

    public static OrderStatusException invalidStatus() {
        return new OrderStatusException(OrderErrorCode.ORDER_INVALID_STATUS);
    }

    public static OrderStatusException alreadyCanceled() {
        return new OrderStatusException(OrderErrorCode.ORDER_ALREADY_CANCELED);
    }

    public static OrderStatusException alreadyCompleted() {
        return new OrderStatusException(OrderErrorCode.ORDER_ALREADY_COMPLETED);
    }
}