package com.musinsam.couponservice.app.application.service.v2.coupon;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v2.coupon.request.ReqCouponIssueDtoApiV2;
import com.musinsam.couponservice.app.application.dto.v2.coupon.response.ResCouponIssueDtoApiV2;

public interface CouponService {
  ResCouponIssueDtoApiV2 issueCoupon(ReqCouponIssueDtoApiV2 request, CurrentUserDtoApiV1 currentUser);

}
