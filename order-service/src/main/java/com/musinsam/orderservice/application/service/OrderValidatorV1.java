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

  /**
   * Validates whether the order can be deleted based on its status.
   *
   * @param orderEntity the order to validate
   * @throws OrderException if the order status does not allow deletion
   */
  public void validateDeletableStatus(OrderEntity orderEntity) {
    List<OrderStatus> deletableStatuses = List.of(
        OrderStatus.CANCELED
    );

    if (!deletableStatuses.contains(orderEntity.getOrderStatus())) {
      throw OrderException.from(OrderErrorCode.ORDER_CANNOT_BE_DELETED);
    }
  }

  /**
   * Validates that the order is in a cancellable status.
   *
   * @param orderEntity the order to validate
   * @throws OrderException if the order cannot be canceled due to its current status
   */
  public void validateCancellableStatus(OrderEntity orderEntity) {
    List<OrderStatus> cancelableStatuses = List.of(
        OrderStatus.PENDING
    );

    if (!cancelableStatuses.contains(orderEntity.getOrderStatus())) {
      throw OrderException.from(OrderErrorCode.ORDER_CANNOT_BE_CANCELED);
    }
  }

  /**
   * Validates that the order status is PENDING.
   *
   * @param orderEntity the order to validate
   * @throws OrderException if the order status is not PENDING
   */
  public void validateOrderStatus(OrderEntity orderEntity) {
    if (!orderEntity.getOrderStatus().equals(OrderStatus.PENDING)) {
      throw OrderException.from(OrderErrorCode.ORDER_INVALID_STATUS);
    }
  }

  /**
   * Validates that the specified user is the owner of the given order.
   *
   * @param orderEntity the order to check ownership for
   * @param userId the user ID to validate against the order's owner
   * @throws CustomException if the user is not the owner of the order
   */
  public void validateOrderOwner(OrderEntity orderEntity, Long userId) {
    if (!orderEntity.getUserId().equals(userId)) {
      throw CustomException.from(CommonErrorCode.FORBIDDEN);
    }
  }
}
