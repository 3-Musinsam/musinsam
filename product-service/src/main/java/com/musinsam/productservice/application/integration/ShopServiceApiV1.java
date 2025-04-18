package com.musinsam.productservice.application.integration;

import java.util.UUID;

public interface ShopServiceApiV1 {

  String getShopNameByShopId(UUID shopId);

  Long getShopManagerIdByShopId(UUID shopId);

}
