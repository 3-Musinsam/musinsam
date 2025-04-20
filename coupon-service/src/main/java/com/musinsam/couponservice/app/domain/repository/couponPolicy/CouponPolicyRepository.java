package com.musinsam.couponservice.app.domain.repository.couponPolicy;

import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponPolicyRepository {
  CouponPolicyEntity save(CouponPolicyEntity couponPolicy);

  Optional<CouponPolicyEntity> findById(UUID id);
  Boolean existsCouponPolicyByName(String name);
  List<CouponPolicyEntity> findAllByCompanyId(UUID companyId);

  Optional<CouponPolicyEntity> findByIdWithPessimisticLock(UUID couponPolicyId);
}
