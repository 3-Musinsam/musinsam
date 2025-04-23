package com.musinsam.orderservice.infrastructure.client;

import com.musinsam.common.config.FeignConfig;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "product-service",
    configuration = FeignConfig.class,
    url = "http://localhost:11002"
)
public interface ProductFeignClient {

  /**
   * 상품 재고 확인 및 차감 요청
   *
   * @param productId 상품 ID
   * @param quantity  수량
   * @return 재고 차감 성공 여부
   */
  @PutMapping("/internal/v1/products/stock/reduce")
  ResponseEntity<Boolean> checkAndReduceStock(
      @RequestParam UUID productId,
      @RequestParam Integer quantity
  );

  /**
   * 상품 재고 복구 요청
   *
   * @param productId 상품 ID
   * @param quantity  복구할 수량
   */
  @PutMapping("/internal/v1/products/stock/restore")
  ResponseEntity<Void> restoreStock(
      @RequestParam UUID productId,
      @RequestParam Integer quantity
  );
}
