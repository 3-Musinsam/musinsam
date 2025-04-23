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
public class OrderStockMangerV1 {

  private final ProductFeignClient productFeignClient;

  /**
   * Attempts to reduce stock for all items in the given order.
   *
   * If stock reduction fails for any item, previously reduced stock is rolled back and {@code false} is returned.
   * Returns {@code true} if stock reduction succeeds for all items.
   *
   * @param orderEntity the order containing items for which stock should be reduced
   * @return {@code true} if stock reduction succeeds for all items; {@code false} otherwise
   */
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

  /**
   * Attempts to reduce the stock for a specific order item by calling the product service.
   *
   * @param item the order item for which to reduce stock
   * @return {@code true} if the stock was successfully reduced; {@code false} otherwise
   */
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

  /**
   * Fallback method invoked when stock reduction for an order item fails after retries.
   *
   * @return always returns {@code false} to indicate stock reduction failure.
   */
  public boolean stockReductionFallback(OrderItemEntity item, Exception e) {
    log.debug("상품 재고 차감 실패: productId={}, quantity={}, 오류={}",
        item.getProductId(), item.getQuantity(), e.getMessage());
    return false;

  }

  /**
   * Restores stock for a list of previously processed order items by calling the product service.
   *
   * @param processedItems the list of order items whose stock should be restored
   * @throws OrderStockRestoreException if restoring stock for any item fails
   */
  @Retry(name = "product-service", fallbackMethod = "restoreProductStockFallback")
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

  /**
   * Restores stock for all items in the given order by invoking the product service.
   *
   * @param orderEntity the order whose items' stock should be restored
   * @throws OrderStockRestoreException if restoring stock for any item fails
   */
  public void restoreProductStock(OrderEntity orderEntity) {
    for (OrderItemEntity item : orderEntity.getOrderItems()) {
      ResponseEntity<Void> response = productFeignClient.restoreStock(item.getProductId(),
          item.getQuantity());

      if (!response.getStatusCode().is2xxSuccessful()) {
        throw new OrderStockRestoreException();
      }
    }
  }

  /**
   * Fallback method invoked when restoring product stock for an order fails.
   *
   * @param orderEntity the order whose stock restoration failed
   * @param e the exception that caused the failure
   * @throws OrderStockRestoreException always thrown to indicate stock restoration failure
   */
  public void restoreProductStockFallback(OrderEntity orderEntity, Exception e) {
    log.debug("주문 취소 시 재고 복원 실패: 주문 ID={}, 오류={}",
        orderEntity.getId(), e.getMessage());

    throw new OrderStockRestoreException();
  }
}
