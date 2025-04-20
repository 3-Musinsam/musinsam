package com.musinsam.shopservice.infrastructure.feign;

import com.musinsam.common.config.FeignConfig;
import com.musinsam.shopservice.application.dto.response.ResShopCouponDtoApiV1;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "coupon-service", configuration = FeignConfig.class)
public interface CouponFeignClientApiV1 {

  @GetMapping("/internal/v1/coupons")
  ResShopCouponDtoApiV1 getCouponsByCompanyId(@RequestParam UUID shopId);
}
