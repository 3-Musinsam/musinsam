package com.musinsam.couponservice.app.presentation.v2;

import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_ISSUE_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v2.coupon.request.ReqCouponIssueDtoApiV2;
import com.musinsam.couponservice.app.application.dto.v2.coupon.response.ResCouponIssueDtoApiV2;
import com.musinsam.couponservice.app.application.service.v2.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v2/coupons")
@RestController("couponControllerV2")
public class CouponController {

  private final CouponService couponService;

  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @PostMapping
  public ResponseEntity<ApiResponse<ResCouponIssueDtoApiV2>> issueCoupon(
      @RequestBody ReqCouponIssueDtoApiV2 request,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResCouponIssueDtoApiV2 response = couponService.issueCoupon(request, currentUser);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_ISSUE_SUCCESS.getCode(),
        COUPON_ISSUE_SUCCESS.getMessage(),
        response
    ));
  }
}
