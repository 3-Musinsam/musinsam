package com.musinsam.couponservice.app.application.service.v1.coupon;


import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.CouponSearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqAvailableCouponDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponValidateDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqShopCouponDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResAvailableCouponDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponCancelDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponValidateDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponsGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResShopCouponDtoApiV1;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponService {

  ResCouponIssueDtoApiV1 issueCoupon(ReqCouponIssueDtoApiV1 request, CurrentUserDtoApiV1 currentUser);

  ResCouponClaimDtoApiV1 claimCoupon(ReqCouponClaimDtoApiV1 request, CurrentUserDtoApiV1 currentUser);

  ResCouponUseDtoApiV1 useCoupon(UUID couponId, ReqCouponUseDtoApiV1 request, CurrentUserDtoApiV1 currentUser);

  Page<ResCouponsGetDtoApiV1> findCouponsByCondition(CouponSearchCondition condition, CurrentUserDtoApiV1 currentUser, Pageable pageable);

  ResCouponGetDtoApiV1 getCoupon(UUID couponId, CurrentUserDtoApiV1 currentUser);

  ResCouponCancelDtoApiV1 cancelCoupon(UUID couponId, CurrentUserDtoApiV1 currentUser);

  void deleteCoupon(UUID couponId, CurrentUserDtoApiV1 currentUser);

  ResShopCouponDtoApiV1 getCouponsByCompanyId(UUID shopId);

  List<ResAvailableCouponDtoApiV1> getAvailableCoupons(Long userId, List<UUID> companyIds, BigDecimal totalAmount);

  ResCouponValidateDtoApiV1 validateCoupon(UUID couponId, ReqCouponValidateDtoApiV1 request);

  void restoreCoupon(UUID id, CurrentUserDtoApiV1 currentUser);
}


