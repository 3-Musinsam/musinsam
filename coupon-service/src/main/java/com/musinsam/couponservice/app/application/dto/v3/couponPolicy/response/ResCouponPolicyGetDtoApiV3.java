package com.musinsam.couponservice.app.application.dto.v3.couponPolicy.response;

import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ResCouponPolicyGetDtoApiV3(
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
    boolean isLimitedIssue
) {

  public static ResCouponPolicyGetDtoApiV3 from(CouponPolicyEntity couponPolicyEntity) {
    return ResCouponPolicyGetDtoApiV3.builder()
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
        .isLimitedIssue(couponPolicyEntity.isLimitedIssue())
        .build();
  }

  public CouponPolicyEntity toEntity() {
    return CouponPolicyEntity.of(
        id,
        name,
        description,
        discountType,
        discountValue,
        minimumOrderAmount,
        maximumDiscountAmount,
        totalQuantity,
        startedAt,
        endedAt,
        companyId,
        isLimitedIssue
    );
  }
}