package com.musinsam.orderservice.application.service;

import com.musinsam.common.exception.CommonErrorCode;
import com.musinsam.common.exception.CustomException;
import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.exception.OrderException;
import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import com.musinsam.orderservice.domain.order.vo.OrderStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidatorV1 {

  public void validateDeletableStatus(OrderEntity orderEntity) {
    List<OrderStatus> deletableStatuses = List.of(
        OrderStatus.CANCELED
    );

    if (!deletableStatuses.contains(orderEntity.getOrderStatus())) {
      throw OrderException.from(OrderErrorCode.ORDER_CANNOT_BE_DELETED);
    }
  }

  public void validateCancellableStatus(OrderEntity orderEntity) {
    List<OrderStatus> cancelableStatuses = List.of(
        OrderStatus.PENDING
    );

    if (!cancelableStatuses.contains(orderEntity.getOrderStatus())) {
      throw OrderException.from(OrderErrorCode.ORDER_CANNOT_BE_CANCELED);
    }
  }

  public void validateOrderStatus(OrderEntity orderEntity) {
    if (!orderEntity.getOrderStatus().equals(OrderStatus.PENDING)) {
      throw OrderException.from(OrderErrorCode.ORDER_INVALID_STATUS);
    }
  }

  public void validateOrderOwner(OrderEntity orderEntity, Long userId) {
    if (!orderEntity.getUserId().equals(userId)) {
      throw CustomException.from(CommonErrorCode.FORBIDDEN);
    }
  }
}
