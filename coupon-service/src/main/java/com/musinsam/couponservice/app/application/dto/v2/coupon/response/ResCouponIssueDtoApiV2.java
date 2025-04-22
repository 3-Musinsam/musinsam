package com.musinsam.couponservice.app.application.dto.v2.coupon.response;

import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import java.time.ZonedDateTime;
import java.util.UUID;

public record ResCouponIssueDtoApiV2(
    UUID couponId,
    UUID couponPolicyId,
    Long userId,
    UUID orderId,
    String couponCode,
    CouponStatus couponStatus,
    ZonedDateTime usedAt,
    ZonedDateTime createdAt,
    Long createdBy,
    ZonedDateTime updatedAt,
    Long updatedBy,
    ZonedDateTime deletedAt,
    Long deletedBy,
    CouponPolicy couponPolicy
) {
  public static ResCouponIssueDtoApiV2 from(CouponEntity couponEntity, CouponPolicyEntity couponPolicyEntity) {
    return new ResCouponIssueDtoApiV2(
        couponEntity.getId(),
        couponEntity.getCouponPolicyEntity().getId(),
        couponEntity.getUserId(),
        couponEntity.getOrderId(),
        couponEntity.getCouponCode(),
        couponEntity.getCouponStatus(),
        couponEntity.getUsedAt(),
        couponEntity.getCreatedAt(),
        couponEntity.getCreatedBy(),
        couponEntity.getUpdatedAt(),
        couponEntity.getUpdatedBy(),
        couponEntity.getDeletedAt(),
        couponEntity.getDeletedBy(),
        CouponPolicy.from(couponPolicyEntity)
    );
  }

  public record CouponPolicy(
      UUID id,
      String name,
      String description,
      DiscountType discountType,
      Integer discountValue,
      Integer minimumOrderAmount,
      Integer maximumDiscountAmount,
      Integer totalQuantity,
      ZonedDateTime startedAt,
      ZonedDateTime endedAt,
      UUID companyId,
      ZonedDateTime createdAt,
      Long createdBy,
      ZonedDateTime updatedAt,
      Long updatedBy,
      ZonedDateTime deletedAt,
      Long deletedBy
  ) {
    public static CouponPolicy from(CouponPolicyEntity couponPolicyEntity) {
      return new CouponPolicy(
          couponPolicyEntity.getId(),
          couponPolicyEntity.getName(),
          couponPolicyEntity.getDescription(),
          couponPolicyEntity.getDiscountType(),
          couponPolicyEntity.getDiscountValue(),
          couponPolicyEntity.getMinimumOrderAmount(),
          couponPolicyEntity.getMaximumDiscountAmount(),
          couponPolicyEntity.getTotalQuantity(),
          couponPolicyEntity.getStartedAt(),
          couponPolicyEntity.getEndedAt(),
          couponPolicyEntity.getCompanyId(),
          couponPolicyEntity.getCreatedAt(),
          couponPolicyEntity.getCreatedBy(),
          couponPolicyEntity.getUpdatedAt(),
          couponPolicyEntity.getUpdatedBy(),
          couponPolicyEntity.getDeletedAt(),
          couponPolicyEntity.getDeletedBy()
      );
    }
  }
}