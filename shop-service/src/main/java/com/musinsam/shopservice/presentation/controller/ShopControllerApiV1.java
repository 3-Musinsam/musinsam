package com.musinsam.shopservice.presentation.controller;

import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;
import static com.musinsam.shopservice.global.config.ShopResponseCode.SHOP_CREATE_SUCCESS;
import static com.musinsam.shopservice.global.config.ShopResponseCode.SHOP_DELETE_SUCCESS;
import static com.musinsam.shopservice.global.config.ShopResponseCode.SHOP_GET_COUPON_LIST_SUCCESS;
import static com.musinsam.shopservice.global.config.ShopResponseCode.SHOP_GET_LIST_SUCCESS;
import static com.musinsam.shopservice.global.config.ShopResponseCode.SHOP_GET_SUCCESS;
import static com.musinsam.shopservice.global.config.ShopResponseCode.SHOP_UPDATE_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.shopservice.application.dto.request.ReqShopPostDtoApiV1;
import com.musinsam.shopservice.application.dto.request.ReqShopPutByShopIdDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopDeleteByShopIdDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopGetByShopIdDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopGetCouponByShopIdDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopGetDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopPostDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopPutByShopIdDtoApiV1;
import com.musinsam.shopservice.application.service.ShopServiceApiV1;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/shops")
public class ShopControllerApiV1 {

  private final ShopServiceApiV1 shopServiceApiV1;

  /**
   * 상점 등록
   */
  @PostMapping
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<ResShopPostDtoApiV1>> createShop(
      @Valid @RequestBody ReqShopPostDtoApiV1 dto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_CREATE_SUCCESS.getCode(),
        SHOP_CREATE_SUCCESS.getMessage(),
        shopServiceApiV1.postBy(dto, currentUser)
    ));
  }

  /**
   * 상점 조회
   */
  @GetMapping
  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<ResShopGetDtoApiV1>> getShopList(
      @Valid Pageable pageable,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_GET_LIST_SUCCESS.getCode(),
        SHOP_GET_LIST_SUCCESS.getMessage(),
        shopServiceApiV1.getBy(pageable, currentUser)
    ));
  }

  /**
   * 상점 상세 조회
   */
  @GetMapping("/{shopId}")
  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<ResShopGetByShopIdDtoApiV1>> getShop(
      @PathVariable UUID shopId,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_GET_SUCCESS.getCode(),
        SHOP_GET_SUCCESS.getMessage(),
        shopServiceApiV1.getByShopId(shopId, currentUser)
    ));
  }

  /**
   * 상점 수정
   */
  @PutMapping("/{shopId}")
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<ResShopPutByShopIdDtoApiV1>> updateShop(
      @PathVariable UUID shopId,
      @Valid @RequestBody ReqShopPutByShopIdDtoApiV1 dto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_UPDATE_SUCCESS.getCode(),
        SHOP_UPDATE_SUCCESS.getMessage(),
        shopServiceApiV1.putByShopId(shopId, dto, currentUser)
    ));
  }

  /**
   * 상점 삭제
   */
  @DeleteMapping("/{shopId}")
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<ResShopDeleteByShopIdDtoApiV1>> deleteShop(
      @PathVariable UUID shopId,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_DELETE_SUCCESS.getCode(),
        SHOP_DELETE_SUCCESS.getMessage(),
        shopServiceApiV1.deleteByShopId(shopId, currentUser)
    ));
  }


  /**
   * 상점에 적용된 쿠폰 조회
   */
  @GetMapping("/{shopId}/coupon")
  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<ResShopGetCouponByShopIdDtoApiV1>> getCouponList(
      @PathVariable UUID shopId,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_GET_COUPON_LIST_SUCCESS.getCode(),
        SHOP_GET_COUPON_LIST_SUCCESS.getMessage(),
        shopServiceApiV1.couponGetByShopId(shopId, currentUser)
    ));
  }
}
