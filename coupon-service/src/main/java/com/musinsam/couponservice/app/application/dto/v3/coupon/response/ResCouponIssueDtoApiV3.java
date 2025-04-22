package com.musinsam.couponservice.app.application.dto.v3.coupon.response;

import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
public record ResCouponIssueDtoApiV3(
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

  public static ResCouponIssueDtoApiV3 from(CouponEntity couponEntity, CouponPolicyEntity couponPolicyEntity) {
    return ResCouponIssueDtoApiV3.builder()
        .couponId(couponEntity.getId())
        .couponPolicyId(couponEntity.getCouponPolicyEntity().getId())
        .userId(couponEntity.getUserId())
        .orderId(couponEntity.getOrderId())
        .couponCode(couponEntity.getCouponCode())
        .couponStatus(couponEntity.getCouponStatus())
        .usedAt(couponEntity.getUsedAt())
        .createdAt(couponEntity.getCreatedAt())
        .createdBy(couponEntity.getCreatedBy())
        .updatedAt(couponEntity.getUpdatedAt())
        .updatedBy(couponEntity.getUpdatedBy())
        .deletedAt(couponEntity.getDeletedAt())
        .deletedBy(couponEntity.getDeletedBy())
        .couponPolicy(CouponPolicy.from(couponPolicyEntity))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CouponPolicy {
    private UUID id;
    private String name;
    private String description;
    private DiscountType discountType;
    private Integer discountValue;
    private Integer minimumOrderAmount;
    private Integer maximumDiscountAmount;
    private Integer totalQuantity;
    private ZonedDateTime startedAt;
    private ZonedDateTime endedAt;
    private UUID companyId;
    private ZonedDateTime createdAt;
    private Long createdBy;
    private ZonedDateTime updatedAt;
    private Long updatedBy;
    private ZonedDateTime deletedAt;
    private Long deletedBy;

    public static CouponPolicy from(CouponPolicyEntity couponPolicyEntity) {
      return CouponPolicy.builder()
          .id(couponPolicyEntity.getId())
          .name(couponPolicyEntity.getName())
          .description(couponPolicyEntity.getDescription())
          .discountType(couponPolicyEntity.getDiscountType())
          .discountValue(couponPolicyEntity.getDiscountValue())
          .minimumOrderAmount(couponPolicyEntity.getMinimumOrderAmount())
          .maximumDiscountAmount(couponPolicyEntity.getMaximumDiscountAmount())
          .totalQuantity(couponPolicyEntity.getTotalQuantity())
          .startedAt(couponPolicyEntity.getStartedAt())
          .endedAt(couponPolicyEntity.getEndedAt())
          .companyId(couponPolicyEntity.getCompanyId())
          .createdAt(couponPolicyEntity.getCreatedAt())
          .createdBy(couponPolicyEntity.getCreatedBy())
          .updatedAt(couponPolicyEntity.getUpdatedAt())
          .updatedBy(couponPolicyEntity.getUpdatedBy())
          .deletedAt(couponPolicyEntity.getDeletedAt())
          .deletedBy(couponPolicyEntity.getDeletedBy())
          .build();
    }
  }
}
