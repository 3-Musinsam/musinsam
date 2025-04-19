package com.musinsam.couponservice.app.application.dto.v1.coupon.response;

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

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResAvailableCouponDtoApiV1 {
  private UUID couponId;
  private Long userId;
  private String couponCode;
  private CouponStatus couponStatus;
  private CouponPolicy couponPolicy;

  public static ResAvailableCouponDtoApiV1 from(CouponEntity couponEntity, CouponPolicyEntity couponPolicyEntity) {
    return ResAvailableCouponDtoApiV1.builder()
        .couponId(couponEntity.getId())
        .userId(couponEntity.getUserId())
        .couponCode(couponEntity.getCouponCode())
        .couponStatus(couponEntity.getCouponStatus())
        .couponPolicy(CouponPolicy.from(couponPolicyEntity))
        .build();

  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CouponPolicy {
    private UUID couponPolicyId;
    private String name;
    private String description;
    private DiscountType discountType;
    private Integer discountValue;
    private Integer minimumOrderAmount;
    private Integer maximumDiscountAmount;
    private ZonedDateTime startedAt;
    private ZonedDateTime endedAt;
    private UUID companyId;

    public static CouponPolicy from(CouponPolicyEntity couponPolicyEntity) {
      return CouponPolicy.builder()
          .couponPolicyId(couponPolicyEntity.getId())
          .name(couponPolicyEntity.getName())
          .description(couponPolicyEntity.getDescription())
          .discountType(couponPolicyEntity.getDiscountType())
          .discountValue(couponPolicyEntity.getDiscountValue())
          .minimumOrderAmount(couponPolicyEntity.getMinimumOrderAmount())
          .maximumDiscountAmount(couponPolicyEntity.getMaximumDiscountAmount())
          .startedAt(couponPolicyEntity.getStartedAt())
          .endedAt(couponPolicyEntity.getEndedAt())
          .companyId(couponPolicyEntity.getCompanyId())
          .build();
    }
  }
}
