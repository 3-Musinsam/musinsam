package com.musinsam.orderservice.application.service;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.entity.OrderItemEntity;
import com.musinsam.orderservice.domain.order.exception.OrderStockRestoreException;
import com.musinsam.orderservice.infrastructure.client.ProductFeignClient;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStockManagerV1 {

  private final ProductFeignClient productFeignClient;

  public boolean checkAndReduceStock(OrderEntity orderEntity) {

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
  public boolean attemptStockReduction(OrderItemEntity item) {
    ResponseEntity<Boolean> response = productFeignClient.checkAndReduceStock(
        item.getProductId(), item.getQuantity());

    if (response.getStatusCode().is2xxSuccessful()
        && Boolean.TRUE.equals(response.getBody())) {
      return true;
    }

    return false;
  }

  public boolean stockReductionFallback(OrderItemEntity item, Exception e) {
    log.debug("상품 재고 차감 실패: productId={}, quantity={}, 오류={}",
        item.getProductId(), item.getQuantity(), e.getMessage());
    return false;

  }

  @Retry(name = "product-service", fallbackMethod = "rollbackStockReductionFallback")
  public void rollbackStockReduction(List<OrderItemEntity> processedItems) {
    if (processedItems.isEmpty()) {
      return;
    }

    for (OrderItemEntity item : processedItems) {
      ResponseEntity<Void> response = productFeignClient.restoreStock(item.getProductId(),
          item.getQuantity());

      if (!response.getStatusCode().is2xxSuccessful()) {
        log.debug("재고 롤백 요청 실패: 상품 ID={}, 응답코드={}", item.getProductId(), response.getStatusCode());
        throw new OrderStockRestoreException();
      }
    }

    log.debug("모든 상품 재고 롤백 완료: 처리된 상품 수={}", processedItems.size());
  }

  public void rollbackStockReductionFallback(List<OrderItemEntity> processedItems, Exception e) {
    log.error("재고 롤백 과정에서 복구 오류 발생: {}", e.getMessage());
  }

  @Retry(name = "product-service", fallbackMethod = "restoreProductStockFallback")
  public void restoreProductStock(OrderEntity orderEntity) {
    for (OrderItemEntity item : orderEntity.getOrderItems()) {
      ResponseEntity<Void> response = productFeignClient.restoreStock(item.getProductId(),
          item.getQuantity());

      if (!response.getStatusCode().is2xxSuccessful()) {
        throw new OrderStockRestoreException();
      }
    }
  }

  public void restoreProductStockFallback(OrderEntity orderEntity, Exception e) {
    log.debug("주문 취소 시 재고 복원 실패: 주문 ID={}, 오류={}",
        orderEntity.getId(), e.getMessage());

    throw new OrderStockRestoreException();
  }
}
