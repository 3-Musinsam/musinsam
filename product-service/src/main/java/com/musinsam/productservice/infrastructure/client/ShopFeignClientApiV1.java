package com.musinsam.productservice.infrastructure.client;

import com.musinsam.productservice.application.integration.ShopClientApiV1;
import com.musinsam.productservice.infrastructure.dto.res.ResShopGetByProductIdDtoApiV1;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "shop-service")
public interface ShopFeignClientApiV1 extends ShopClientApiV1 {

  // 상점 정보
  @GetMapping("/internal/v1/shops/{shopId}")
  ResShopGetByProductIdDtoApiV1 getShopInfo(@PathVariable UUID shopId);

}
