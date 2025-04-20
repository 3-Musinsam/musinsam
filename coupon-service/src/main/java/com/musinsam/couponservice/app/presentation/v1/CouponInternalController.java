package com.musinsam.couponservice.app.presentation.v1;


import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponValidateDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResAvailableCouponDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponValidateDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResShopCouponDtoApiV1;
import com.musinsam.couponservice.app.application.service.v1.coupon.CouponService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/internal/v1/coupons")
@RestController
public class CouponInternalController {

  private final CouponService couponService;

  @GetMapping
  public ResponseEntity<ResShopCouponDtoApiV1> getCouponsByCompanyId(
      @RequestParam UUID shopId) {

    ResShopCouponDtoApiV1 response = couponService.getCouponsByCompanyId(shopId);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/{id}/use")
  public ResponseEntity<ResCouponUseDtoApiV1> useCoupon(
      @PathVariable UUID id,
      @RequestBody ReqCouponUseDtoApiV1 request,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResCouponUseDtoApiV1 response = couponService.useCoupon(id, request, currentUser);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/available")
  public ResponseEntity<List<ResAvailableCouponDtoApiV1>> couponAvailable(
      @RequestParam Long userId,
      @RequestParam List<UUID> companyIds,
      @RequestParam BigDecimal totalAmount
  ) {
    List<ResAvailableCouponDtoApiV1> coupons = couponService.getAvailableCoupons(userId, companyIds, totalAmount);

    return ResponseEntity.ok(coupons);
  }

  @PostMapping("/{id}/validate")
  public ResponseEntity<ResCouponValidateDtoApiV1> couponValidate(
      @PathVariable UUID id,
      @RequestBody ReqCouponValidateDtoApiV1 request
  ) {
    ResCouponValidateDtoApiV1 response = couponService.validateCoupon(id, request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{id}/restore")
  public ResponseEntity<Void> couponRestore(
      @PathVariable UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    couponService.restoreCoupon(id, currentUser);
    return ResponseEntity.ok().build();
  }
}
