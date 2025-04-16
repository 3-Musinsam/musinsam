package com.musinsam.couponservice.app.application.service.v1.coupon;


import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponIssueDtoApiV1;

public interface CouponService {

  ResCouponIssueDtoApiV1 issueCoupon(ReqCouponIssueDtoApiV1 request, CurrentUserDtoApiV1 currentUser);
}
