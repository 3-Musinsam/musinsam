package com.musinsam.couponservice.app.application.dto.v1.coupon.response;

import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResCouponIssueDtoApiV1 {
  private UUID couponId;
  private UUID couponPolicyId;
  private Long userId;
  private UUID orderId;
  private String couponCode;
  private CouponStatus couponStatus;
  private ZonedDateTime usedAt;
  private ZonedDateTime createdAt;
  private Long createdBy;
  private ZonedDateTime updatedAt;
  private Long updatedBy;
  private ZonedDateTime deletedAt;
  private Long deletedBy;

  public static ResCouponIssueDtoApiV1 from(CouponEntity couponEntity) {
    return ResCouponIssueDtoApiV1.builder()
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
        .build();
  }


}
