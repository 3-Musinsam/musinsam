package com.musinsam.couponservice.app.infrastructure.persistence.jpa.couponPolicy;

import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponPolicyJpaRepository extends
    JpaRepository<CouponPolicyEntity, UUID>,
    CouponPolicyRepository {

}
