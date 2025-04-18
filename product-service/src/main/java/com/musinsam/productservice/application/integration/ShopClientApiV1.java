package com.musinsam.productservice.application.integration;

import com.musinsam.productservice.infrastructure.dto.res.ResShopGetByProductIdDtoApiV1;
import java.util.UUID;

public interface ShopClientApiV1 {

  ResShopGetByProductIdDtoApiV1 getShopInfo(UUID shopId);

}
