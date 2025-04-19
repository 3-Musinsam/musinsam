package com.musinsam.couponservice.app.infrastructure;

import com.musinsam.couponservice.app.application.client.ShopClient;
import com.musinsam.couponservice.app.infrastructure.feign.ShopFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ShopClientImpl implements ShopClient {

  private final ShopFeignClient shopFeignClient;


  @Override
  public Boolean existsShopById(String shopId) {
    ResponseEntity<Boolean> response = shopFeignClient.existsShopById(shopId);
    return Boolean.TRUE.equals(response.getBody()); 
  }
}
