package com.musinsam.couponservice.app.presentation.v4;

import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v4.coupon.request.ReqCouponIssueDtoApiV4;
import com.musinsam.couponservice.app.application.service.v4.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v4/coupons")
@RestController("couponControllerV4")
public class CouponController {

  private final CouponService couponService;

  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @PostMapping("/issue")
  public ResponseEntity<ApiResponse<Void>> issueCoupon(
      @RequestBody ReqCouponIssueDtoApiV4 request,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    couponService.requestCouponIssue(request, currentUser);

    return ResponseEntity.ok().build();
  }
}
