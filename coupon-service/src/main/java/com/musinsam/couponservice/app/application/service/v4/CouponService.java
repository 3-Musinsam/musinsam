package com.musinsam.couponservice.app.application.service.v4;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v3.coupon.request.ReqCouponUseDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.coupon.response.ResCouponUseDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v4.coupon.request.ReqCouponIssueDtoApiV4;
import com.musinsam.couponservice.app.application.dto.v4.coupon.response.IssueMessage;
import java.util.UUID;

public interface CouponService {
  void requestCouponIssue(ReqCouponIssueDtoApiV4 request, CurrentUserDtoApiV1 currentUser);

  void issueCoupon(IssueMessage message);

  ResCouponUseDtoApiV3 useCoupon(UUID couponId, ReqCouponUseDtoApiV3 request, CurrentUserDtoApiV1 currentUser);
}
