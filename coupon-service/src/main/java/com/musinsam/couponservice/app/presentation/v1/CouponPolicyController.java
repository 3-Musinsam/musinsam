package com.musinsam.couponservice.app.presentation.v1;

import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;
import static com.musinsam.couponservice.app.global.config.CouponPolicyResponseCode.COUPON_POLICY_DELETE_SUCCESS;
import static com.musinsam.couponservice.app.global.config.CouponPolicyResponseCode.COUPON_POLICIES_GET_SUCCESS;
import static com.musinsam.couponservice.app.global.config.CouponPolicyResponseCode.COUPON_POLICY_GET_SUCCESS;
import static com.musinsam.couponservice.app.global.config.CouponResponseCode.COUPON_ISSUE_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.ReqCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPoliciesGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.domain.vo.DiscountType;
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

@RequestMapping("/v1/coupon-policies")
@RestController
public class CouponPolicyController {

  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  @PostMapping
  public ResponseEntity<ApiResponse<ResCouponPolicyIssueDtoApiV1>> issueCouponPolicy(
      @RequestBody ReqCouponPolicyIssueDtoApiV1 reqCouponPolicyIssueDtoApiV1,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_ISSUE_SUCCESS.getCode(),
        COUPON_ISSUE_SUCCESS.getMessage(),
        null
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ResCouponPolicyGetDtoApiV1>> getCouponPolicy(
      @PathVariable UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_POLICY_GET_SUCCESS.getCode(),
        COUPON_POLICY_GET_SUCCESS.getMessage(),
        null
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @GetMapping
  public ResponseEntity<ApiResponse<PagedModel<ResCouponPoliciesGetDtoApiV1>>> getCouponPolicies(
      @CurrentUser CurrentUserDtoApiV1 currentUser,
      @RequestParam(required = false) UUID couponPolicyId,
      @RequestParam(required = false) String policyName,
      @RequestParam(required = false) String policyDescription,
      @RequestParam(required = false) DiscountType discountType,
      @RequestParam(required = false) UUID companyId,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime startedFrom,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime startedTo,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime endedFrom,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime endedTo,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime createdFrom,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime createdTo,
      @PageableDefault(size = 20)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          @SortDefault(sort = "id", direction = Sort.Direction.DESC)
      }) Pageable pageable
  ) {

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_POLICIES_GET_SUCCESS.getCode(),
        COUPON_POLICIES_GET_SUCCESS.getMessage(),
        null
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deleteCouponPolicy(
      @PathVariable UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_POLICY_DELETE_SUCCESS.getCode(),
        COUPON_POLICY_DELETE_SUCCESS.getMessage(),
        null
    ));
  }
}
