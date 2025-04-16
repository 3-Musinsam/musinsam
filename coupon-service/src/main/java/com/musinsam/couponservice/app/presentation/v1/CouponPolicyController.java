package com.musinsam.couponservice.app.presentation.v1;

import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_ISSUE_SUCCESS;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyResponseCode.COUPON_POLICIES_GET_SUCCESS;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyResponseCode.COUPON_POLICY_GET_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.CouponPolicySearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.ReqCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPoliciesGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.application.service.couponPolicy.CouponPolicyService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/coupon-policies")
@RestController
public class CouponPolicyController {

  private final CouponPolicyService couponPolicyService;

  @CustomPreAuthorize(userRoleType = {ROLE_MASTER})
  @PostMapping
  public ResponseEntity<ApiResponse<ResCouponPolicyIssueDtoApiV1>> issueCouponPolicy(
      @RequestBody ReqCouponPolicyIssueDtoApiV1 request,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResCouponPolicyIssueDtoApiV1 response = couponPolicyService.issueCouponPolicy(request, currentUser);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_ISSUE_SUCCESS.getCode(),
        COUPON_ISSUE_SUCCESS.getMessage(),
        response
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ResCouponPolicyGetDtoApiV1>> getCouponPolicy(
      @PathVariable UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    ResCouponPolicyGetDtoApiV1 response = couponPolicyService.getCouponPolicy(id, currentUser);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_POLICY_GET_SUCCESS.getCode(),
        COUPON_POLICY_GET_SUCCESS.getMessage(),
        response
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_MASTER})
  @GetMapping
  public ResponseEntity<ApiResponse<Page<ResCouponPoliciesGetDtoApiV1>>> getCouponPoliciesByCondition(
      @ParameterObject CouponPolicySearchCondition condition,
      @CurrentUser CurrentUserDtoApiV1 currentUser,
      @PageableDefault(size = 10)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          @SortDefault(sort = "id", direction = Sort.Direction.DESC)
      }) Pageable pageable
  ) {

    Page<ResCouponPoliciesGetDtoApiV1> response = couponPolicyService.getCouponPoliciesByCondition(condition,
        currentUser, pageable);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_POLICIES_GET_SUCCESS.getCode(),
        COUPON_POLICIES_GET_SUCCESS.getMessage(),
        response
    ));
  }

//  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
//  @DeleteMapping("/{id}")
//  public ResponseEntity<ApiResponse<Void>> deleteCouponPolicy(
//      @PathVariable UUID id,
//      @CurrentUser CurrentUserDtoApiV1 currentUser
//  ) {
//    return ResponseEntity.ok(new ApiResponse<>(
//        COUPON_POLICY_DELETE_SUCCESS.getCode(),
//        COUPON_POLICY_DELETE_SUCCESS.getMessage(),
//        null
//    ));
//  }
}
