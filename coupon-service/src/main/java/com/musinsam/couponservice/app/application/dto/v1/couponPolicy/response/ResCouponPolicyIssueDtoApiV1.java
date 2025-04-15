package com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response;

import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResCouponPolicyIssueDtoApiV1 {

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
  private UUID sellerId;

  public static ResCouponPolicyIssueDtoApiV1 from(CouponPolicyEntity couponPolicyEntity) {
    return ResCouponPolicyIssueDtoApiV1.builder()
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
        .sellerId(couponPolicyEntity.getSellerId())
        .build();
  }
}
