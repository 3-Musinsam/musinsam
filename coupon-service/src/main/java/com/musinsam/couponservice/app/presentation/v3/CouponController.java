package com.musinsam.couponservice.app.presentation.v3;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v3/coupons")
@RestController("couponControllerV3")
public class CouponController {

//  private final CouponService couponService;
//
//  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
//  @PostMapping("/issue")
//  public ResponseEntity<ApiResponse<ResCouponIssueDtoApiV3>> issueCoupon(
//      @RequestBody ReqCouponIssueDtoApiV3 request,
//      @CurrentUser CurrentUserDtoApiV1 currentUser
//  ) {
//    ResCouponIssueDtoApiV2 response = couponService.issueCoupon(request, currentUser);
//
//    return ResponseEntity.ok(new ApiResponse<>(
//        COUPON_ISSUE_SUCCESS.getCode(),
//        COUPON_ISSUE_SUCCESS.getMessage(),
//        response
//    ));
//  }

}
