package com.musinsam.couponservice.app.application.service.v1.coupon;


import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.CouponSearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponsGetDtoApiV1;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponService {

  ResCouponIssueDtoApiV1 issueCoupon(ReqCouponIssueDtoApiV1 request, CurrentUserDtoApiV1 currentUser);

  ResCouponClaimDtoApiV1 claimCoupon(ReqCouponClaimDtoApiV1 request, CurrentUserDtoApiV1 currentUser);

  ResCouponUseDtoApiV1 useCoupon(UUID couponId, ReqCouponUseDtoApiV1 request, CurrentUserDtoApiV1 currentUser);

  Page<ResCouponsGetDtoApiV1> findCouponsByCondition(CouponSearchCondition condition, CurrentUserDtoApiV1 currentUser, Pageable pageable);
}


