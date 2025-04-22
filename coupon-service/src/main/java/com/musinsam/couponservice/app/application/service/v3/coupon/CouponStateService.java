package com.musinsam.couponservice.app.application.service.v3.coupon;

import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;

public interface CouponStateService {
  void updateCouponState(CouponEntity couponEntity);
}
