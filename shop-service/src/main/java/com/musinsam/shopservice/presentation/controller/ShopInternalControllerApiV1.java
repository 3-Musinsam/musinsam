package com.musinsam.shopservice.presentation.controller;

import static com.musinsam.shopservice.infrastructure.excepcion.ShopResponseCode.SHOP_GET_COUPON_LIST_SUCCESS;
import static com.musinsam.shopservice.infrastructure.excepcion.ShopResponseCode.SHOP_GET_LIST_SUCCESS;
import static com.musinsam.shopservice.infrastructure.excepcion.ShopResponseCode.SHOP_GET_SUCCESS;

import com.musinsam.common.response.ApiResponse;
import com.musinsam.shopservice.application.dto.request.ReqShopGetSearchDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResInternalShopGetByShopIdDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopGetCouponByShopIdDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopGetDtoApiV1;
import com.musinsam.shopservice.application.service.ShopServiceApiV1;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/shops")
public class ShopInternalControllerApiV1 {

  private final ShopServiceApiV1 shopServiceApiV1;

  /**
   * 상점 단건 조회
   */
  @GetMapping("/{shopId}")
  public ResponseEntity<ApiResponse<ResInternalShopGetByShopIdDtoApiV1>> internalGetShop(
      @PathVariable UUID shopId
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_GET_SUCCESS.getCode(),
        SHOP_GET_SUCCESS.getMessage(),
        shopServiceApiV1.internalGetShop(shopId)
    ));
  }

  /**
   * 상점 검색
   */
  @GetMapping("/search")
  public ResponseEntity<ApiResponse<ResShopGetDtoApiV1>> internalGetShopList(
      @Valid Pageable pageable,
      @RequestBody ReqShopGetSearchDtoApiV1.ShopDto dto
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_GET_LIST_SUCCESS.getCode(),
        SHOP_GET_LIST_SUCCESS.getMessage(),
        shopServiceApiV1.internalGetBy(pageable, dto)
    ));
  }

  /**
   * 상점에 적용된 쿠폰 조회
   */
  @GetMapping("/{shopId}/coupon")
  public ResponseEntity<ApiResponse<ResShopGetCouponByShopIdDtoApiV1>> getCouponList(
      @PathVariable UUID shopId
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_GET_COUPON_LIST_SUCCESS.getCode(),
        SHOP_GET_COUPON_LIST_SUCCESS.getMessage(),
        shopServiceApiV1.couponGetByShopId(shopId)
    ));
  }
}
