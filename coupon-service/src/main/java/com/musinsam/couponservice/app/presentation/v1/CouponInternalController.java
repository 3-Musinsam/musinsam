package com.musinsam.couponservice.app.presentation.v1;


import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_GET_SUCCESS;

import com.musinsam.common.response.ApiResponse;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResShopCouponDtoApiV1;
import com.musinsam.couponservice.app.application.service.v1.coupon.CouponService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/internal/v1/coupons")
@RestController
public class CouponInternalController {

  private final CouponService couponService;

  @GetMapping
  public ResponseEntity<ApiResponse<ResShopCouponDtoApiV1>> getCouponsByCompanyId(
      @RequestParam UUID shopId) {

    ResShopCouponDtoApiV1 response = couponService.getCouponsByCompanyId(shopId);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_GET_SUCCESS.getCode(),
        COUPON_GET_SUCCESS.getMessage(),
        response
    ));
  }


}
