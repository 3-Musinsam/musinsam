package com.musinsam.couponservice.app.domain.repository.coupon;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.CouponSearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponsGetDtoApiV1;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponQueryRepository {
  Page<ResCouponsGetDtoApiV1> findCouponsByCondition(
      CouponSearchCondition condition,
      CurrentUserDtoApiV1 currentUser,
      Pageable pageable);

  List<CouponEntity> findAvailableCoupons(
      Long userId,
      List<UUID> companyIds,
      BigDecimal totalAmount,
      ZonedDateTime now
  );

  boolean existsByUserIdAndCouponPolicyId(Long userId, UUID couponPolicyId);

}
