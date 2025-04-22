package com.musinsam.couponservice.app.infrastructure.persistence.jpa.coupon;

import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponRepository;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, UUID>, CouponRepository {

  @Query("SELECT COUNT(c) FROM CouponEntity c WHERE c.couponPolicyEntity.id = :policyId")
  Long countByCouponPolicyId(@Param("policyId") UUID policyId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT c FROM CouponEntity c WHERE c.id = :couponId")
  Optional<CouponEntity> findByIdWithLock(@Param("couponId") UUID couponId);
}
