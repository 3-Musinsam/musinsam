package com.musinsam.couponservice.app.application.service.v3.coupon;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v2.coupon.response.ResCouponIssueDtoApiV2;
import com.musinsam.couponservice.app.application.dto.v3.coupon.request.ReqCouponIssueDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.coupon.request.ReqCouponUseDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.coupon.response.ResCouponIssueDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.coupon.response.ResCouponUseDtoApiV3;
import java.util.UUID;

public interface CouponService {
  ResCouponIssueDtoApiV3 issueCoupon(ReqCouponIssueDtoApiV3 request, CurrentUserDtoApiV1 currentUser);

  ResCouponUseDtoApiV3 useCoupon(UUID couponId, ReqCouponUseDtoApiV3 request, CurrentUserDtoApiV1 currentUser);
}
