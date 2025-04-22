package com.musinsam.couponservice.app.application.service.v3.coupon;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v2.coupon.response.ResCouponIssueDtoApiV2;
import com.musinsam.couponservice.app.application.dto.v3.coupon.request.ReqCouponIssueDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.coupon.response.ResCouponIssueDtoApiV3;

public interface CouponService {
  ResCouponIssueDtoApiV3 issueCoupon(ReqCouponIssueDtoApiV3 request, CurrentUserDtoApiV1 currentUser);
}
