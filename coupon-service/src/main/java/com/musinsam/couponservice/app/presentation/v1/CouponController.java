package com.musinsam.couponservice.app.presentation.v1;


import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPONS_GET_SUCCESS;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_CLAIM_SUCCESS;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_ISSUE_SUCCESS;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_USE_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.CouponSearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponsGetDtoApiV1;
import com.musinsam.couponservice.app.application.service.v1.coupon.CouponService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/coupons")
@RestController
public class CouponController {

  private final CouponService couponService;

  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  @PostMapping
  public ResponseEntity<ApiResponse<ResCouponIssueDtoApiV1>> issueCoupon(
      @RequestBody ReqCouponIssueDtoApiV1 request,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResCouponIssueDtoApiV1 response = couponService.issueCoupon(request, currentUser);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_ISSUE_SUCCESS.getCode(),
        COUPON_ISSUE_SUCCESS.getMessage(),
        response
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @PostMapping("/claim")
  public ResponseEntity<ApiResponse<ResCouponClaimDtoApiV1>> claimCoupon(
      @RequestBody ReqCouponClaimDtoApiV1 request,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    ResCouponClaimDtoApiV1 response = couponService.claimCoupon(request, currentUser);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_CLAIM_SUCCESS.getCode(),
        COUPON_CLAIM_SUCCESS.getMessage(),
        response
    ));
  }


  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @PostMapping("/{id}/use")
  public ResponseEntity<ApiResponse<ResCouponUseDtoApiV1>> useCoupon(
      @PathVariable UUID id,
      @RequestBody ReqCouponUseDtoApiV1 request,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResCouponUseDtoApiV1 response = couponService.useCoupon(id, request, currentUser);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_USE_SUCCESS.getCode(),
        COUPON_USE_SUCCESS.getMessage(),
        response
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @GetMapping
  public ResponseEntity<ApiResponse<Page<ResCouponsGetDtoApiV1>>> getCoupons(
      @CurrentUser CurrentUserDtoApiV1 currentUser,
      @ParameterObject CouponSearchCondition condition,
      @PageableDefault(size = 10)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          @SortDefault(sort = "id", direction = Sort.Direction.DESC)
      }) Pageable pageable
  ) {

    Page<ResCouponsGetDtoApiV1> response = couponService.findCouponsByCondition(condition, currentUser, pageable);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPONS_GET_SUCCESS.getCode(),
        COUPONS_GET_SUCCESS.getMessage(),
        response
    ));
  }
//
//
//  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
//  @GetMapping("/{id}")
//  public ResponseEntity<ApiResponse<ResCouponGetDtoApiV1>> getCoupon(
//      @PathVariable UUID id,
//      @CurrentUser CurrentUserDtoApiV1 currentUser
//  ) {
//    return ResponseEntity.ok(new ApiResponse<>(
//        COUPON_GET_SUCCESS.getCode(),
//        COUPON_GET_SUCCESS.getMessage(),
//        null
//    ));
//  }
//
//  @CustomPreAuthorize(userRoleType = {ROLE_USER})
//  @PostMapping("/{id}/cancel")
//  public ResponseEntity<ApiResponse<ResCouponCancelDtoApiV1>> cancelCoupon(
//      @PathVariable UUID id,
//      @CurrentUser CurrentUserDtoApiV1 currentUser
//  ) {
//    return ResponseEntity.ok(new ApiResponse<>(
//        COUPON_CANCEL_SUCCESS.getCode(),
//        COUPON_CANCEL_SUCCESS.getMessage(),
//        null
//    ));
//  }
//
//  @CustomPreAuthorize(userRoleType = {ROLE_USER})
//  @DeleteMapping("/{id}")
//  public ResponseEntity<ApiResponse<Void>> deleteCoupon(
//      @PathVariable UUID id,
//      @CurrentUser CurrentUserDtoApiV1 currentUser
//  ) {
//    return ResponseEntity.ok(new ApiResponse<>(
//        COUPON_DELETE_SUCCESS.getCode(),
//        COUPON_DELETE_SUCCESS.getMessage(),
//        null
//    ));
//  }
}
