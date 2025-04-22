package com.musinsam.couponservice.app.presentation.v3;

import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponResponseCode.COUPON_ISSUE_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v3.coupon.request.ReqCouponIssueDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.coupon.response.ResCouponIssueDtoApiV3;
import com.musinsam.couponservice.app.application.service.v3.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v3/coupons")
@RestController("couponControllerV3")
public class CouponController {

  private final CouponService couponService;

  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @PostMapping("/issue")
  public ResponseEntity<ApiResponse<ResCouponIssueDtoApiV3>> issueCoupon(
      @RequestBody ReqCouponIssueDtoApiV3 request,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResCouponIssueDtoApiV3 response = couponService.issueCoupon(request, currentUser);

    return ResponseEntity.ok(new ApiResponse<>(
        COUPON_ISSUE_SUCCESS.getCode(),
        COUPON_ISSUE_SUCCESS.getMessage(),
        response
    ));
  }
}
