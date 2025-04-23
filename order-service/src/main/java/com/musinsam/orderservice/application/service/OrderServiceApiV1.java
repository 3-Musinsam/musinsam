package com.musinsam.orderservice.application.service;

import com.musinsam.common.exception.CommonErrorCode;
import com.musinsam.common.exception.CustomException;
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
import com.musinsam.orderservice.domain.order.exception.OrderStockRestoreException;
import com.musinsam.orderservice.domain.order.repository.OrderRepository;
import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import com.musinsam.orderservice.domain.order.vo.OrderStatus;
import com.musinsam.orderservice.infrastructure.client.ProductFeignClient;
import com.querydsl.core.types.Predicate;
import io.github.resilience4j.retry.annotation.Retry;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceApiV1 {

  private final OrderRepository orderRepository;
  private final ProductFeignClient productFeignClient;

  @Transactional
  public ResOrderPostDtoApiV1 createOrder(ReqOrderPostDtoApiV1 requestDto, Long userId) {

    OrderEntity orderEntity = requestDto.getOrder().toEntityWith(userId);

    validateOrderOwner(orderEntity, userId);
    validateOrderStatus(orderEntity);

    boolean stockAvailable = checkAndReduceStock(orderEntity);

    if (!stockAvailable) {
      throw OrderException.from(OrderErrorCode.ORDER_PRODUCT_NOT_AVAILABLE);
    }

    OrderEntity savedOrder = orderRepository.save(orderEntity);

    return ResOrderPostDtoApiV1.of(savedOrder);
  }

  @Transactional(readOnly = true)
  public ResOrderGetByIdDtoApiV1 getOrder(UUID orderId, Long userId) {

    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));

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
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));

    validateOrderOwner(orderEntity, userId);
    validateOrderStatus(orderEntity);

    boolean stockAvailable = checkAndReduceStock(orderEntity);

    if (!stockAvailable) {
      throw OrderException.from(OrderErrorCode.ORDER_PRODUCT_NOT_AVAILABLE);
    }

    requestDto.getOrder().updateEntity(orderEntity);

    return ResOrderPutDtoApiV1.of(orderEntity);
  }

  @Transactional
  public ResOrderPostCancelDtoApiV1 cancelOrder(UUID orderId, ReqOrderPostCancelDtoApiV1 requestDto,
      Long userId) {

    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));

    validateOrderOwner(orderEntity, userId);

    validateCancellableStatus(orderEntity);

    orderEntity.cancel(
        requestDto.getOrder().getCancelType(),
        requestDto.getOrder().getCancelReason()
    );

    restoreProductStock(orderEntity);

    return ResOrderPostCancelDtoApiV1.of(orderEntity);
  }

  @Transactional
  public void deleteOrder(UUID orderId, Long userId) {
    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));

    validateOrderOwner(orderEntity, userId);
    validateDeletableStatus(orderEntity);

    ZoneId zoneId = ZoneId.systemDefault();

    orderEntity.softDelete(userId, zoneId);
    orderEntity.updateOrderStatus(OrderStatus.DELETED, "In service");

    for (OrderItemEntity item : orderEntity.getOrderItems()) {
      item.softDelete(userId, zoneId);
    }
  }

  private boolean checkAndReduceStock(OrderEntity orderEntity) {

    log.debug("주문 상품 재고 확인 및 차감 시작: 주문 ID={}", orderEntity.getId());
    List<OrderItemEntity> processedItems = new ArrayList<>();

    for (OrderItemEntity item : orderEntity.getOrderItems()) {
      log.debug("상품 재고 차감 시도: 상품 ID={}, 수량={}", item.getProductId(), item.getQuantity());
      boolean success = attemptStockReduction(item);

      if (!success) {
        log.debug("상품 재고 차감 실패, 롤백 시작: 상품 ID={}", item.getProductId());
        rollbackStockReduction(processedItems);
        return false;
      }

      processedItems.add(item);
    }
    log.debug("모든 상품 재고 차감 성공: 주문 ID={}", orderEntity.getId());
    return true;
  }

  @Retry(name = "product-service", fallbackMethod = "stockReductionFallback")
  private boolean attemptStockReduction(OrderItemEntity item) {
    ResponseEntity<Boolean> response = productFeignClient.checkAndReduceStock(
        item.getProductId(), item.getQuantity());

    if (response.getStatusCode().is2xxSuccessful() &&
        Boolean.TRUE.equals(response.getBody())) {
      return true;
    }

    return false;
  }

  private boolean stockReductionFallback(OrderItemEntity item, Exception e) {

    log.debug("상품 재고 차감 실패: productId={}, quantity={}, 오류={}",
        item.getProductId(), item.getQuantity(), e.getMessage());
    return false;

  }

  private void rollbackStockReduction(List<OrderItemEntity> processedItems) {
    if (processedItems.isEmpty()) {
      return;
    }

    for (OrderItemEntity item : processedItems) {
      productFeignClient.restoreStock(item.getProductId(), item.getQuantity());
    }
  }

  private void restoreProductStock(OrderEntity orderEntity) {
    for (OrderItemEntity item : orderEntity.getOrderItems()) {
      ResponseEntity<Void> response = productFeignClient.restoreStock(item.getProductId(),
          item.getQuantity());

      if (!response.getStatusCode().is2xxSuccessful()) {
        throw new OrderStockRestoreException();
      }
    }
  }

  private void restoreProductStockFallback(OrderEntity orderEntity, Exception e) {
    log.debug("주문 취소 시 재고 복원 실패: 주문 ID={}, 오류={}",
        orderEntity.getId(), e.getMessage());

    throw new OrderStockRestoreException();
  }


  private void validateDeletableStatus(OrderEntity orderEntity) {
    List<OrderStatus> deletableStatuses = List.of(
        OrderStatus.CANCELED
    );

    if (!deletableStatuses.contains(orderEntity.getOrderStatus())) {
      throw OrderException.from(OrderErrorCode.ORDER_CANNOT_BE_DELETED);
    }
  }

  private void validateCancellableStatus(OrderEntity orderEntity) {
    List<OrderStatus> cancelableStatuses = List.of(
        OrderStatus.PENDING
    );

    if (!cancelableStatuses.contains(orderEntity.getOrderStatus())) {
      throw OrderException.from(OrderErrorCode.ORDER_CANNOT_BE_CANCELED);
    }
  }

  private void validateOrderStatus(OrderEntity orderEntity) {
    if (!orderEntity.getOrderStatus().equals(OrderStatus.PENDING)) {
      throw OrderException.from(OrderErrorCode.ORDER_INVALID_STATUS);
    }
  }

  private void validateOrderOwner(OrderEntity orderEntity, Long userId) {
    if (!orderEntity.getUserId().equals(userId)) {
      throw CustomException.from(CommonErrorCode.FORBIDDEN);
    }
  }
}
