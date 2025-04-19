package com.musinsam.couponservice.app.infrastructure.feign;

import com.musinsam.couponservice.app.infrastructure.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "shop-service", configuration = FeignClientConfig.class)
public interface ShopFeignClient {

  @GetMapping("/internal/v1/shops/{shopId}")
  ResponseEntity<Boolean> existsShopById(@PathVariable String shopId);
}
