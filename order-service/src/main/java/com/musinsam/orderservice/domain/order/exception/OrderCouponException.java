package com.musinsam.orderservice.domain.order.exception;

import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import lombok.Getter;

@Getter
public class OrderCouponException extends OrderException {

    public OrderCouponException() {
        super(OrderErrorCode.ORDER_COUPON_NOT_APPLICABLE);
    }

    public static OrderCouponException notApplicable() {
        return new OrderCouponException();
    }
}