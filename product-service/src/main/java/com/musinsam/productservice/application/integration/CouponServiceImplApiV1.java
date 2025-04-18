package com.musinsam.productservice.application.integration;

import com.musinsam.productservice.infrastructure.client.CouponFeignClientApiV1;
import com.musinsam.productservice.infrastructure.dto.res.ResShopCouponDtoApiV1;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponServiceImplApiV1 implements CouponServiceApiV1 {

  private final CouponFeignClientApiV1 couponFeignClient;

  @Override
  public ResShopCouponDtoApiV1 getShopCouponList(UUID shopId) {
    return couponFeignClient.getCouponList(shopId);
  }
}
