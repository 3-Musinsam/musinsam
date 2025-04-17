package com.musinsam.orderservice.application.service;

import com.musinsam.common.exception.CommonErrorCode;
import com.musinsam.common.exception.CustomException;
import com.musinsam.orderservice.app.global.response.OrderErrorCode;
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
import com.musinsam.orderservice.domain.order.repository.OrderRepository;
import com.musinsam.orderservice.domain.order.vo.OrderStatus;
import com.querydsl.core.types.Predicate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceApiV1 {

  private final OrderRepository orderRepository;

  @Transactional
  public ResOrderPostDtoApiV1 createOrder(ReqOrderPostDtoApiV1 requestDto, Long userId) {

    OrderEntity orderEntity = requestDto.getOrder().toEntityWith(userId);

    validateOrderOwner(orderEntity, userId);
    validateOrderStatus(orderEntity);

    // TODO: 상품 서비스에 재고 확인 요청 및 차감
    //  boolean stockAvailable = validateAndReserveStock(requestDto);
    boolean stockAvailable = true;

    if (!stockAvailable) {
      throw CustomException.from(OrderErrorCode.ORDER_PRODUCT_NOT_AVAILABLE);
    }

    OrderEntity savedOrder = orderRepository.save(orderEntity);

    // TODO: 주문 이벤트 발행
    //  publishOrderCreatedEvent(savedOrder);

    return ResOrderPostDtoApiV1.of(savedOrder);
  }

  @Transactional(readOnly = true)
  public ResOrderGetByIdDtoApiV1 getOrder(UUID orderId, Long userId) {

    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> CustomException.from(OrderErrorCode.ORDER_NOT_FOUND));

    validateOrderOwner(orderEntity, userId);

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
    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> CustomException.from(OrderErrorCode.ORDER_NOT_FOUND));

    validateOrderOwner(orderEntity, userId);
    validateOrderStatus(orderEntity);

    // TODO: 상품 서비스에 재고 확인 요청 및 차감
    //  boolean stockAvailable = validateAndReserveStock(requestDto);
    boolean stockAvailable = true;

    if (!stockAvailable) {
      throw CustomException.from(OrderErrorCode.ORDER_PRODUCT_NOT_AVAILABLE);
    }

    requestDto.getOrder().updateEntity(orderEntity);

    // TODO: 이벤트 발행
    //  publishOrderUpdateEvent(orderEntity);

    return ResOrderPutDtoApiV1.of(orderEntity);
  }

  @Transactional
  public ResOrderPostCancelDtoApiV1 cancelOrder(UUID orderId, ReqOrderPostCancelDtoApiV1 requestDto,
      Long userId) {

    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> CustomException.from(OrderErrorCode.ORDER_NOT_FOUND));

    validateOrderOwner(orderEntity, userId);

    validateCancellableStatus(orderEntity);

    orderEntity.cancel(
        requestDto.getOrder().getCancelType(),
        requestDto.getOrder().getCancelReason()
    );

    // TODO: 재고 복구 요청

    // TODO: 취소 이벤트 발행

    return ResOrderPostCancelDtoApiV1.of(orderEntity);
  }

  @Transactional
  public void deleteOrder(UUID orderId, Long userId) {
    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> CustomException.from(OrderErrorCode.ORDER_NOT_FOUND));

    validateOrderOwner(orderEntity, userId);
    validateDeletableStatus(orderEntity);

    ZoneId zoneId = ZoneId.systemDefault();

    orderEntity.softDelete(userId, zoneId);
    orderEntity.updateOrderStatus(OrderStatus.DELETED, "In service");

    for (OrderItemEntity item : orderEntity.getOrderItems()) {
      item.softDelete(userId, zoneId);
    }
  }

  private void validateDeletableStatus(OrderEntity orderEntity) {
    List<OrderStatus> deletableStatuses = List.of(
        OrderStatus.CANCELED
    );

    if (!deletableStatuses.contains(orderEntity.getOrderStatus())) {
      throw CustomException.from(OrderErrorCode.ORDER_CANNOT_BE_DELETED);
    }
  }

  private void validateCancellableStatus(OrderEntity orderEntity) {
    List<OrderStatus> cancelableStatuses = List.of(
        OrderStatus.PENDING
    );

    if (!cancelableStatuses.contains(orderEntity.getOrderStatus())) {
      throw CustomException.from(OrderErrorCode.ORDER_CANNOT_BE_CANCELED);
    }
  }

  private void validateOrderStatus(OrderEntity orderEntity) {
    if (!orderEntity.getOrderStatus().equals(OrderStatus.PENDING)) {
      throw CustomException.from(OrderErrorCode.ORDER_INVALID_STATUS);
    }
  }

  private void validateOrderOwner(OrderEntity orderEntity, Long userId) {
    if (!orderEntity.getUserId().equals(userId)) {
      throw CustomException.from(CommonErrorCode.FORBIDDEN);
    }
  }
}
