package com.musinsam.couponservice.app.presentation.v3;

import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_ISSUE_SUCCESS;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyResponseCode.COUPON_POLICY_GET_SUCCESS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v3.couponPolicy.request.ReqCouponPolicyIssueDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.couponPolicy.response.ResCouponPolicyGetDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.couponPolicy.response.ResCouponPolicyIssueDtoApiV3;
import com.musinsam.couponservice.app.application.service.v3.couponPolicy.CouponPolicyService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v3/coupon-policies")
@RestController("couponPolicyControllerV3")
public class CouponPolicyController {

  private final CouponPolicyService couponPolicyService;

  @CustomPreAuthorize(userRoleType = {ROLE_MASTER})
  @PostMapping("/issue")
  public ResponseEntity<ApiResponse<ResCouponPolicyIssueDtoApiV3>> issueCouponPolicy(
      @RequestBody ReqCouponPolicyIssueDtoApiV3 request,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) throws JsonProcessingException {

    ResCouponPolicyIssueDtoApiV3 response = couponPolicyService.issueCouponPolicy(request, currentUser);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_ISSUE_SUCCESS.getCode(),
        COUPON_ISSUE_SUCCESS.getMessage(),
        response
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_COMPANY, ROLE_MASTER})
  @GetMapping("/{couponPolicyId}")
  public ResponseEntity<ApiResponse<ResCouponPolicyGetDtoApiV3>> getCouponPolicy(
      @PathVariable UUID couponPolicyId,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    ResCouponPolicyGetDtoApiV3 response = couponPolicyService.getCouponPolicy(couponPolicyId, currentUser);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_POLICY_GET_SUCCESS.getCode(),
        COUPON_POLICY_GET_SUCCESS.getMessage(),
        response
    ));
  }
}
