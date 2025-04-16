package com.musinsam.couponservice.app.domain.repository.coupon;

import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import java.util.Optional;
import java.util.UUID;

public interface CouponRepository {

  Optional<CouponEntity> findById(UUID id);
  Optional<CouponEntity> findByCouponCode(String couponCode);
  CouponEntity save(CouponEntity couponEntity);
  Boolean existsCouponByCouponCode(String couponCode);
}
