package com.musinsam.productservice.presentation.controller;

import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;
import static com.musinsam.productservice.global.config.ProductResponseCode.PRODUCT_APPLY_COUPON_SUCCESS;
import static com.musinsam.productservice.global.config.ProductResponseCode.PRODUCT_CREATE_SUCCESS;
import static com.musinsam.productservice.global.config.ProductResponseCode.PRODUCT_DELETE_COUPON_SUCCESS;
import static com.musinsam.productservice.global.config.ProductResponseCode.PRODUCT_DELETE_SUCCESS;
import static com.musinsam.productservice.global.config.ProductResponseCode.PRODUCT_GET_COUPON_SUCCESS;
import static com.musinsam.productservice.global.config.ProductResponseCode.PRODUCT_GET_LIST_SUCCESS;
import static com.musinsam.productservice.global.config.ProductResponseCode.PRODUCT_GET_STOCK_SUCCESS;
import static com.musinsam.productservice.global.config.ProductResponseCode.PRODUCT_GET_SUCCESS;
import static com.musinsam.productservice.global.config.ProductResponseCode.PRODUCT_UPDATE_STOCK_SUCCESS;
import static com.musinsam.productservice.global.config.ProductResponseCode.PRODUCT_UPDATE_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.productservice.application.dto.request.ReqProductPostCouponDtoApiV1;
import com.musinsam.productservice.application.dto.request.ReqProductPostDtoApiV1;
import com.musinsam.productservice.application.dto.request.ReqProductPutByProductIdDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetByProductIdDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetCouponDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetStockDtoApiV1;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
public class ProductControllerApiV1 {

  /**
   * 상품 등록
   */
  @PostMapping
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<Void>> createProduct(
      @RequestBody ReqProductPostDtoApiV1 dto,
      @CurrentUser CurrentUserDtoApiV1 currentUser) {
    return ResponseEntity.ok(new ApiResponse<>(
        PRODUCT_CREATE_SUCCESS.getCode(),
        PRODUCT_CREATE_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 단일 상품 조회
   */
  @GetMapping("/{product_id}")
  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<ResProductGetByProductIdDtoApiV1>> getProduct(
      @PathVariable("product_id") UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        PRODUCT_GET_SUCCESS.getCode(),
        PRODUCT_GET_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 상품 목록 조회
   */
  @GetMapping
  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<Page<ResProductGetDtoApiV1>>> getProductList(
      @CurrentUser CurrentUserDtoApiV1 currentUser,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        PRODUCT_GET_LIST_SUCCESS.getCode(),
        PRODUCT_GET_LIST_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 상품 수정
   */
  @PutMapping("/{product_id}")
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<Void>> updateProduct(
      @PathVariable("product_id") UUID id,
      @RequestBody ReqProductPutByProductIdDtoApiV1 dto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        PRODUCT_UPDATE_SUCCESS.getCode(),
        PRODUCT_UPDATE_STOCK_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 상품 삭제
   */
  @DeleteMapping("/{product_id}")
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<Void>> deleteProduct(
      @PathVariable("product_id") UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        PRODUCT_DELETE_SUCCESS.getCode(),
        PRODUCT_DELETE_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 재고 조회
   */
  @GetMapping("/{product_id}/stock")
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<ResProductGetStockDtoApiV1>> getProductStock(
      @PathVariable("product_id") UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        PRODUCT_GET_STOCK_SUCCESS.getCode(),
        PRODUCT_GET_STOCK_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 재고 수정
   */
  @PatchMapping("/{product_id}/stock")
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<Void>> updateProductStock(
      @PathVariable("product_id") UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        PRODUCT_UPDATE_STOCK_SUCCESS.getCode(),
        PRODUCT_UPDATE_STOCK_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 상품에 쿠폰 추가
   */
  @PostMapping("/{product_id}/coupons")
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<Void>> applyCoupon(
      @PathVariable("product_id") UUID productId,
      @CurrentUser CurrentUserDtoApiV1 currentUser,
      @RequestBody ReqProductPostCouponDtoApiV1 dto
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        PRODUCT_APPLY_COUPON_SUCCESS.getCode(),
        PRODUCT_APPLY_COUPON_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 적용된 쿠폰 조회
   */
  @GetMapping("/{product_id}/coupons")
  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<List<ResProductGetCouponDtoApiV1>>> getCouponList(
      @PathVariable("product_id") UUID productId,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        PRODUCT_GET_COUPON_SUCCESS.getCode(),
        PRODUCT_GET_COUPON_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 적용된 쿠폰 삭제
   */
  @DeleteMapping("/{product_id}/coupons/{coupon_id}")
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<Void>> deleteCoupon(
      @PathVariable("product_id") UUID productId,
      @PathVariable("coupon_id") UUID couponId,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        PRODUCT_DELETE_COUPON_SUCCESS.getCode(),
        PRODUCT_DELETE_COUPON_SUCCESS.getMessage(),
        null
    ));
  }


}
