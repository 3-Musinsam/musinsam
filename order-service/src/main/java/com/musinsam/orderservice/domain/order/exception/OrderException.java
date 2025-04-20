package com.musinsam.orderservice.domain.order.exception;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.exception.ErrorCode;
import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import lombok.Getter;

@Getter
public class OrderException extends CustomException {

    public OrderException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static OrderException from(OrderErrorCode errorCode) {
        return new OrderException(errorCode);
    }
}