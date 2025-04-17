package com.musinsam.couponservice.app.domain.repository.coupon;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.CouponSearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponsGetDtoApiV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponQueryRepository {
  Page<ResCouponsGetDtoApiV1> findCouponsByCondition(
      CouponSearchCondition condition,
      CurrentUserDtoApiV1 currentUser,
      Pageable pageable);
}
