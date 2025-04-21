package com.musinsam.shopservice.presentation.controller;

import com.musinsam.shopservice.application.dto.request.ReqShopGetSearchDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResInternalShopGetByShopIdDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopCouponDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopGetDtoApiV1;
import com.musinsam.shopservice.application.service.ShopInternalServiceApiV1;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/shops")
public class ShopInternalControllerApiV1 {

  private final ShopInternalServiceApiV1 shopInternalServiceApiV1;

  /**
   * 상점 단건 조회
   */
  @GetMapping("/{shopId}")
  public ResponseEntity<ResInternalShopGetByShopIdDtoApiV1> internalGetShop(
      @PathVariable UUID shopId
  ) {
    return ResponseEntity.ok(shopInternalServiceApiV1.internalGetShop(shopId));
  }

  /**
   * 상점 검색
   */
  @GetMapping("/search")
  public ResponseEntity<ResShopGetDtoApiV1> internalGetShopListSearch(
      @Valid Pageable pageable,
      @RequestBody ReqShopGetSearchDtoApiV1.Shop dto
  ) {
    return ResponseEntity.ok(shopInternalServiceApiV1.internalGetShopListSearch(pageable, dto));
  }

  /**
   * 상점에 적용된 쿠폰 조회
   */
  @GetMapping("/coupons")
  public ResponseEntity<ResShopCouponDtoApiV1> internalCouponGetByShopId(
      @RequestParam UUID shopId
  ) {
    return ResponseEntity.ok(shopInternalServiceApiV1.internalCouponGetByShopId(shopId));
  }

  /**
   * 상점 존재 여부 조회
   */
  @GetMapping("/{shopId}/exists")
  public ResponseEntity<Boolean> internalExistsShopById(
      @PathVariable UUID shopId
  ) {
    return ResponseEntity.ok(shopInternalServiceApiV1.internalExistsShopById(shopId));
  }

}
