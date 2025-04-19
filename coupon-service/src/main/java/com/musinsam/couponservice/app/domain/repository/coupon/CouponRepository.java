package com.musinsam.couponservice.app.domain.repository.coupon;

import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponRepository {

  Optional<CouponEntity> findById(UUID id);
  Optional<CouponEntity> findByCouponCode(String couponCode);
  CouponEntity save(CouponEntity couponEntity);
  Boolean existsCouponByCouponCode(String couponCode);
  List<CouponEntity> findAllByCouponPolicyEntity_CompanyId(UUID companyId);
  List<CouponEntity> findValidCouponsByUserId(Long userId);
//  boolean existsByUserIdAndCouponPolicyId(Long userId, UUID couponPolicyId); // querydsl 로 다시 구현
}
