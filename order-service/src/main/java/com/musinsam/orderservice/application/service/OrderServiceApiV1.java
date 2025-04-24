package com.musinsam.orderservice.application.service;

import com.musinsam.orderservice.application.dto.request.ReqOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPutDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderGetByIdDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderGetDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPutDtoApiV1;
import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.entity.OrderItemEntity;
import com.musinsam.orderservice.domain.order.exception.OrderException;
import com.musinsam.orderservice.domain.order.repository.OrderRepository;
import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import com.musinsam.orderservice.domain.order.vo.OrderStatus;
import com.querydsl.core.types.Predicate;
import java.time.ZoneId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceApiV1 {

  private final OrderRepository orderRepository;
  private final OrderValidatorV1 orderValidatorV1;
  private final OrderStockManagerV1 orderStockManagerV1;

  /**
   * Creates a new order for the specified user, validates ownership and status, checks and reduces stock, and returns the created order details.
   *
   * @param requestDto the order creation request data
   * @param userId the ID of the user placing the order
   * @return a response DTO representing the created order
   * @throws OrderException if stock is unavailable or validation fails
   */
  @Transactional
  public ResOrderPostDtoApiV1 createOrder(ReqOrderPostDtoApiV1 requestDto, Long userId) {

    OrderEntity orderEntity = requestDto.getOrder().toEntityWith(userId);

    orderValidatorV1.validateOrderOwner(orderEntity, userId);
    orderValidatorV1.validateOrderStatus(orderEntity);

    OrderEntity savedOrder = orderRepository.save(orderEntity);

    boolean stockAvailable = orderStockManagerV1.checkAndReduceStock(orderEntity);

    if (!stockAvailable) {
      throw OrderException.from(OrderErrorCode.ORDER_PRODUCT_NOT_AVAILABLE);
    }

    return ResOrderPostDtoApiV1.of(savedOrder);
  }

  /**
   * Retrieves the details of a specific order for a given user.
   *
   * @param orderId the unique identifier of the order
   * @param userId the ID of the user requesting the order
   * @return a response DTO containing the order details
   * @throws OrderException if the order is not found or the user does not own the order
   */
  @Transactional(readOnly = true)
  public ResOrderGetByIdDtoApiV1 getOrder(UUID orderId, Long userId) {

    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));

    orderValidatorV1.validateOrderOwner(orderEntity, userId);

    return ResOrderGetByIdDtoApiV1.of(orderEntity);
  }

  @Transactional(readOnly = true)
  public ResOrderGetDtoApiV1 getOrderList(
      Predicate predicate,
      Pageable pageable,
      String searchKeyword,
      Long userId
  ) {
    Page<OrderEntity> orderPage = orderRepository.findAll(predicate, pageable);

    return ResOrderGetDtoApiV1.of(orderPage);
  }

  /**
   * Updates the status of an existing order and adjusts product stock accordingly.
   *
   * @param orderId the unique identifier of the order to update
   * @param requestDto the request data containing the new order status and details
   * @param userId the ID of the user performing the update
   * @return a response DTO representing the updated order
   * @throws OrderException if the order is not found, the user is not the owner, the order status is invalid for update, or if required product stock is unavailable
   */
  @Transactional
  public ResOrderPutDtoApiV1 updateOrderStatus(UUID orderId, ReqOrderPutDtoApiV1 requestDto,
      Long userId) {
    OrderEntity orderEntity = orderRepository.findByIdWithOrderItemsForUpdate(orderId)
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));

    orderValidatorV1.validateOrderOwner(orderEntity, userId);
    orderValidatorV1.validateOrderStatus(orderEntity);

    orderStockManagerV1.restoreProductStock(orderEntity);

    requestDto.getOrder().updateEntity(orderEntity);

    boolean stockAvailable = orderStockManagerV1.checkAndReduceStock(orderEntity);

    if (!stockAvailable) {
      throw OrderException.from(OrderErrorCode.ORDER_PRODUCT_NOT_AVAILABLE);
    }

    return ResOrderPutDtoApiV1.of(orderEntity);
  }

  /**
   * Cancels an order for the specified user and restores product stock.
   *
   * @param orderId the unique identifier of the order to cancel
   * @param requestDto the cancellation request containing cancel type and reason
   * @param userId the ID of the user requesting the cancellation
   * @return a response DTO representing the cancelled order
   * @throws OrderException if the order is not found or cannot be cancelled
   */
  @Transactional
  public ResOrderPostCancelDtoApiV1 cancelOrder(UUID orderId, ReqOrderPostCancelDtoApiV1 requestDto,
      Long userId) {

    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));

    orderValidatorV1.validateOrderOwner(orderEntity, userId);

    orderValidatorV1.validateCancellableStatus(orderEntity);

    orderEntity.cancel(
        requestDto.getOrder().getCancelType(),
        requestDto.getOrder().getCancelReason()
    );

    orderStockManagerV1.restoreProductStock(orderEntity);

    return ResOrderPostCancelDtoApiV1.of(orderEntity);
  }

  /**
   * Soft deletes an order and its items if the user is the owner and the order is deletable.
   *
   * Marks the order and all associated items as deleted, updates the order status to DELETED, and records deletion metadata.
   *
   * @param orderId the unique identifier of the order to delete
   * @param userId the ID of the user requesting the deletion
   * @throws OrderException if the order is not found, the user is not the owner, or the order cannot be deleted
   */
  @Transactional
  public void deleteOrder(UUID orderId, Long userId) {
    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));

    orderValidatorV1.validateOrderOwner(orderEntity, userId);
    orderValidatorV1.validateDeletableStatus(orderEntity);

    ZoneId zoneId = ZoneId.systemDefault();

    orderEntity.softDelete(userId, zoneId);
    orderEntity.updateOrderStatus(OrderStatus.DELETED, "In service");

    for (OrderItemEntity item : orderEntity.getOrderItems()) {
      item.softDelete(userId, zoneId);
    }
  }
}
