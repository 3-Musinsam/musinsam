package com.musinsam.couponservice.app.application.service.v3.coupon;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v3.coupon.request.ReqCouponIssueDtoApiV3;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;

public interface CouponRedisService {
  CouponEntity issueCoupon(ReqCouponIssueDtoApiV3 request, CurrentUserDtoApiV1 currentUser);
}
