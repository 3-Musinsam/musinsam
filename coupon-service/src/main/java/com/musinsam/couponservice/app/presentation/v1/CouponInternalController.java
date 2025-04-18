package com.musinsam.couponservice.app.presentation.v1;


import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_GET_SUCCESS;

import com.musinsam.common.response.ApiResponse;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqShopCouponDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResShopCouponDtoApiV1;
import com.musinsam.couponservice.app.application.service.v1.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/internal/v1/coupons")
@RestController
public class CouponInternalController {

  private final CouponService couponService;

  @GetMapping
  public ResponseEntity<ApiResponse<ResShopCouponDtoApiV1>> getCoupon(
      @RequestBody ReqShopCouponDtoApiV1 request) {

    ResShopCouponDtoApiV1 response = couponService.getCouponsByCompanyId(request);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_GET_SUCCESS.getCode(),
        COUPON_GET_SUCCESS.getMessage(),
        response
    ));
  }
}
