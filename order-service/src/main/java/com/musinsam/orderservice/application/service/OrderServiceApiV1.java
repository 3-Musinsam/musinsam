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
  private final OrderStockMangerV1 orderStockMangerV1;

  @Transactional
  public ResOrderPostDtoApiV1 createOrder(ReqOrderPostDtoApiV1 requestDto, Long userId) {

    OrderEntity orderEntity = requestDto.getOrder().toEntityWith(userId);

    orderValidatorV1.validateOrderOwner(orderEntity, userId);
    orderValidatorV1.validateOrderStatus(orderEntity);

    OrderEntity savedOrder = orderRepository.save(orderEntity);

    boolean stockAvailable = orderStockMangerV1.checkAndReduceStock(orderEntity);

    if (!stockAvailable) {
      throw OrderException.from(OrderErrorCode.ORDER_PRODUCT_NOT_AVAILABLE);
    }

    return ResOrderPostDtoApiV1.of(savedOrder);
  }

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

  @Transactional
  public ResOrderPutDtoApiV1 updateOrderStatus(UUID orderId, ReqOrderPutDtoApiV1 requestDto,
      Long userId) {
    OrderEntity orderEntity = orderRepository.findByIdWithOrderItemsForUpdate(orderId)
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));

    orderValidatorV1.validateOrderOwner(orderEntity, userId);
    orderValidatorV1.validateOrderStatus(orderEntity);

    orderStockMangerV1.restoreProductStock(orderEntity);

    requestDto.getOrder().updateEntity(orderEntity);

    boolean stockAvailable = orderStockMangerV1.checkAndReduceStock(orderEntity);

    if (!stockAvailable) {
      throw OrderException.from(OrderErrorCode.ORDER_PRODUCT_NOT_AVAILABLE);
    }

    return ResOrderPutDtoApiV1.of(orderEntity);
  }

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

    orderStockMangerV1.restoreProductStock(orderEntity);

    return ResOrderPostCancelDtoApiV1.of(orderEntity);
  }

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
