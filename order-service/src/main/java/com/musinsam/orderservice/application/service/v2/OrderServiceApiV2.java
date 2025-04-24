package com.musinsam.orderservice.application.service.v2;

import com.musinsam.orderservice.application.dto.request.v2.ReqOrderPostCancelDtoApiV2;
import com.musinsam.orderservice.application.dto.request.v2.ReqOrderPostDtoApiV2;
import com.musinsam.orderservice.application.dto.response.v2.ResOrderPostCancelDtoApiV2;
import com.musinsam.orderservice.application.dto.response.v2.ResOrderPostDtoApiV2;
import com.musinsam.orderservice.application.service.OrderStockManagerV1;
import com.musinsam.orderservice.application.service.OrderValidatorV1;
import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.entity.OrderItemEntity;
import com.musinsam.orderservice.domain.order.exception.OrderException;
import com.musinsam.orderservice.domain.order.repository.OrderRepository;
import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import com.musinsam.orderservice.infrastructure.config.OrderRedisLockTemplate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceApiV2 {

  private final OrderRepository orderRepository;
  private final OrderValidatorV1 orderValidator;
  private final OrderStockManagerV1 orderStockManager;
  private final OrderRedisLockTemplate orderRedisLockTemplate;

  @Transactional
  public ResOrderPostDtoApiV2 createOrder(ReqOrderPostDtoApiV2 requestDto, Long userId) {

    OrderEntity orderEntity = requestDto.getOrder().toEntityWith(userId);

    orderValidator.validateOrderOwner(orderEntity, userId);
    orderValidator.validateOrderStatus(orderEntity);

    List<UUID> orderItemIds = orderEntity.getOrderItems().stream()
        .map(OrderItemEntity::getProductId)
        .toList();

    return orderRedisLockTemplate.executeWithLock(orderItemIds, () -> {
      OrderEntity savedOrder = orderRepository.save(orderEntity);

      boolean stockAvailable = orderStockManager.checkAndReduceStock(orderEntity);
      if (!stockAvailable) {
        throw OrderException.from(OrderErrorCode.ORDER_PRODUCT_NOT_AVAILABLE);
      }

      return ResOrderPostDtoApiV2.of(savedOrder);
    });
  }

  @Transactional
  public ResOrderPostCancelDtoApiV2 cancelOrder(UUID orderId, ReqOrderPostCancelDtoApiV2 requestDto,
      Long userId) {
    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));

    orderValidator.validateOrderOwner(orderEntity, userId);
    orderValidator.validateCancellableStatus(orderEntity);

    List<UUID> productIds = orderEntity.getOrderItems().stream()
        .map(OrderItemEntity::getProductId)
        .toList();

    return orderRedisLockTemplate.executeWithLock(productIds, () -> {
      orderEntity.cancel(
          requestDto.getOrder().getCancelType(),
          requestDto.getOrder().getCancelReason()
      );

      orderStockManager.restoreProductStock(orderEntity);

      return ResOrderPostCancelDtoApiV2.of(orderEntity);
    });
  }
}
