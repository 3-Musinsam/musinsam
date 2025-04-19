package com.musinsam.couponservice.app.application.client;

import org.springframework.http.ResponseEntity;

public interface ShopClient {

  Boolean existsShopById(String shopId);

}
