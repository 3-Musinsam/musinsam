package com.musinsam.couponservice.app.presentation.v3;

import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v3.coupon.request.ReqCouponUseDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.coupon.response.ResCouponUseDtoApiV3;
import com.musinsam.couponservice.app.application.service.v3.coupon.CouponService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/internal/v3/coupons")
@RestController("couponInternalControllerV3")
public class CouponInternalController {

  private final CouponService couponService;

  @PostMapping("/{couponId}/use")
  public ResponseEntity<ResCouponUseDtoApiV3> useCoupon(
      @PathVariable UUID couponId,
      @RequestBody ReqCouponUseDtoApiV3 request,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResCouponUseDtoApiV3 response = couponService.useCoupon(couponId, request, currentUser);

    return ResponseEntity.ok(response);
  }
}
