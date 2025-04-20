package com.musinsam.couponservice.app.infrastructure.persistence.jpa.couponPolicy;

import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface CouponPolicyJpaRepository extends
    JpaRepository<CouponPolicyEntity, UUID>,
    CouponPolicyRepository {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT cp FROM CouponPolicyEntity cp WHERE cp.id = :couponPolicyId")
  Optional<CouponPolicyEntity> findByIdWithPessimisticLock(UUID couponPolicyId);

}
