package com.musinsam.productservice.application.integration;

import com.musinsam.productservice.infrastructure.dto.res.ResShopCouponDtoApiV1;
import java.util.UUID;

public interface CouponClientApiV1 {

  ResShopCouponDtoApiV1 getShopCouponList(UUID shopId);

}
