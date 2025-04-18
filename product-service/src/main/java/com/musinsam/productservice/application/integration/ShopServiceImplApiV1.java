package com.musinsam.productservice.application.integration;

import com.musinsam.productservice.infrastructure.client.ShopFeignClientApiV1;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImplApiV1 implements ShopServiceApiV1 {

  private final ShopFeignClientApiV1 shopFeignClient;

  @Override
  public String getShopNameByShopId(UUID shopId) {
    return shopFeignClient.getShopInfo(shopId).getShop().getName();
  }

  @Override
  public Long getShopManagerIdByShopId(UUID shopId) {
    return shopFeignClient.getShopInfo(shopId).getShop().getUserId();
  }

}
