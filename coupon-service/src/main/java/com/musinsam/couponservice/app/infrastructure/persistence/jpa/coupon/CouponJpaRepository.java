package com.musinsam.couponservice.app.infrastructure.persistence.jpa.coupon;

import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, UUID>, CouponRepository {
}
