package com.musinsam.couponservice.app.presentation.v1;


import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPONS_GET_SUCCESS;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_CANCEL_SUCCESS;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_CLAIM_SUCCESS;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_DELETE_SUCCESS;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_GET_SUCCESS;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_ISSUE_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponCancelDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponsGetDtoApiV1;
import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/coupons")
@RestController
public class CouponController {

  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  @PostMapping
  public ResponseEntity<ApiResponse<ResCouponIssueDtoApiV1>> issueCoupon(
      @RequestBody ReqCouponIssueDtoApiV1 reqCouponIssueDtoApiV1,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_ISSUE_SUCCESS.getCode(),
        COUPON_ISSUE_SUCCESS.getMessage(),
        null
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_USER})
  @PostMapping("/claim")
  public ResponseEntity<ApiResponse<ResCouponClaimDtoApiV1>> claimCoupon(
      @RequestBody ReqCouponClaimDtoApiV1 reqCouponClaimDtoApiV1,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_CLAIM_SUCCESS.getCode(),
        COUPON_CLAIM_SUCCESS.getMessage(),
        null
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_USER})
  @PostMapping("/{id}/use")
  public ResponseEntity<ApiResponse<ResCouponUseDtoApiV1>> useCoupon(
      @PathVariable UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_CLAIM_SUCCESS.getCode(),
        COUPON_CLAIM_SUCCESS.getMessage(),
        null
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @GetMapping
  public ResponseEntity<ApiResponse<PagedModel<ResCouponsGetDtoApiV1>>> getCoupons(
      @CurrentUser CurrentUserDtoApiV1 currentUser,
      @RequestParam(required = false) UUID couponPolicyId,
      @RequestParam(required = false) String policyName,
      @RequestParam(required = false) String couponCode,
      @RequestParam(required = false) CouponStatus couponStatus,
      @RequestParam(required = false) DiscountType discountType,
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) UUID orderId,
      @RequestParam(required = false) Long companyId,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime usedFrom,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime usedTo,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime createdFrom,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime createdTo,
      @PageableDefault(size = 20)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          @SortDefault(sort = "id", direction = Sort.Direction.DESC)
      }) Pageable pageable
  ) {

    return ResponseEntity.ok(new ApiResponse<>(
        COUPONS_GET_SUCCESS.getCode(),
        COUPONS_GET_SUCCESS.getMessage(),
        null
    ));
  }


  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ResCouponGetDtoApiV1>> getCoupon(
      @PathVariable UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_GET_SUCCESS.getCode(),
        COUPON_GET_SUCCESS.getMessage(),
        null
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_USER})
  @PostMapping("/{id}/cancel")
  public ResponseEntity<ApiResponse<ResCouponCancelDtoApiV1>> cancelCoupon(
      @PathVariable UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_CANCEL_SUCCESS.getCode(),
        COUPON_CANCEL_SUCCESS.getMessage(),
        null
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_USER})
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deleteCoupon(
      @PathVariable UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_DELETE_SUCCESS.getCode(),
        COUPON_DELETE_SUCCESS.getMessage(),
        null
    ));
  }
}
