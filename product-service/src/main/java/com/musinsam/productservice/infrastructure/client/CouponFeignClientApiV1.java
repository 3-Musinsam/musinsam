package com.musinsam.productservice.infrastructure.client;

import com.musinsam.productservice.infrastructure.dto.res.ResShopCouponDtoApiV1;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "coupon-service")
public interface CouponFeignClientApiV1 {

  // 쿠폰리스트 받기
  @GetMapping("/internal/v1/coupons")
  ResShopCouponDtoApiV1 getCouponList(@RequestParam UUID shopId);

}
