package com.musinsam.couponservice.app.domain.repository.couponPolicy;

import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import java.util.Optional;

public interface CouponPolicyRepository {

  Optional<CouponPolicyEntity> findById(Long id);
  Boolean existsByCouponCode(String couponCode);
}
