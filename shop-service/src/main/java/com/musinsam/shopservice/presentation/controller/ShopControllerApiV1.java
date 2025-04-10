package com.musinsam.shopservice.presentation.controller;

import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;
import static com.musinsam.shopservice.global.config.ShopResponseCode.SHOP_CREATE_SUCCESS;
import static com.musinsam.shopservice.global.config.ShopResponseCode.SHOP_DELETE_SUCCESS;
import static com.musinsam.shopservice.global.config.ShopResponseCode.SHOP_GET_LIST_SUCCESS;
import static com.musinsam.shopservice.global.config.ShopResponseCode.SHOP_GET_SUCCESS;
import static com.musinsam.shopservice.global.config.ShopResponseCode.SHOP_UPDATE_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.shopservice.application.dto.request.ReqShopPostCreateDtoApiV1;
import com.musinsam.shopservice.application.dto.request.ReqShopPostUpdateDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopGetDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopGetListDtoApiV1;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/shops")
public class ShopControllerApiV1 {

  /**
   * 상점 등록
   */
  @PostMapping
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<Void>> createShop(
      @RequestBody ReqShopPostCreateDtoApiV1 dto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_CREATE_SUCCESS.getCode(),
        SHOP_CREATE_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 상점 조회
   */
  @GetMapping
  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<Page<ResShopGetListDtoApiV1>>> getShopList(
      @CurrentUser CurrentUserDtoApiV1 currentUser,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_GET_LIST_SUCCESS.getCode(),
        SHOP_GET_LIST_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 상점 상세 조회
   */
  @GetMapping("/{shop_id}")
  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<ResShopGetDtoApiV1>> getShop(
      @PathVariable("shop_id") UUID shopId,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_GET_SUCCESS.getCode(),
        SHOP_GET_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 상점 수정
   */
  @PostMapping("/{shop_id}")
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<Void>> updateShop(
      @PathVariable("shop_id") UUID shopId,
      @RequestBody ReqShopPostUpdateDtoApiV1 dto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_UPDATE_SUCCESS.getCode(),
        SHOP_UPDATE_SUCCESS.getMessage(),
        null
    ));
  }

  /**
   * 상점 삭제
   */
  @DeleteMapping("/{shop_id}")
  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  public ResponseEntity<ApiResponse<Void>> deleteShop(
      @PathVariable("shop_id") UUID shopId,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        SHOP_DELETE_SUCCESS.getCode(),
        SHOP_DELETE_SUCCESS.getMessage(),
        null
    ));
  }
}
