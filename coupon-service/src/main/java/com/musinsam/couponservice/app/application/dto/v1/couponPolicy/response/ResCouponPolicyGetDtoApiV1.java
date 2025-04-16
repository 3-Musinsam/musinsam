package com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response;

import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResCouponPolicyGetDtoApiV1 {
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

  public static ResCouponPolicyGetDtoApiV1 from(CouponPolicyEntity couponPolicyEntity) {
    return ResCouponPolicyGetDtoApiV1.builder()
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
