package com.musinsam.couponservice.app.domain.repository.coupon;

import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.query.Param;

public interface CouponRepository {

  Optional<CouponEntity> findById(UUID id);

  Optional<CouponEntity> findByCouponCode(String couponCode);

  CouponEntity save(CouponEntity couponEntity);

  Boolean existsCouponByCouponCode(String couponCode);

  List<CouponEntity> findAllByCouponPolicyEntity_CompanyId(UUID companyId);

  List<CouponEntity> findValidCouponsByUserId(Long userId);

  Long countByCouponPolicyId(@Param("policyId") UUID policyId);

  Optional<CouponEntity> findByIdWithLock(UUID couponId);

  //  boolean existsByUserIdAndCouponPolicyId(Long userId, UUID couponPolicyId); // querydsl 로 다시 구현
}
