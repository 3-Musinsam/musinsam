package com.musinsam.couponservice.app.application.dto.v1.coupon.response;

import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.vo.CouponStatus;
import com.musinsam.couponservice.app.domain.vo.DiscountType;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ResCouponIssueDtoApiV1 {

  private Coupon coupon;

  public static ResCouponIssueDtoApiV1 of(CouponEntity couponEntity) {
    return ResCouponIssueDtoApiV1.builder()
        .coupon(Coupon.from(couponEntity))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Coupon {
    private UUID id;
    private String couponCode;
    private CouponStatus couponStatus;
    private ZonedDateTime usedAt;
    private CouponPolicy couponPolicy;

    public static Coupon from(CouponEntity couponEntity) {
      return Coupon.builder()
          .id(couponEntity.getId())
          .couponCode(couponEntity.getCouponCode())
          .couponStatus(couponEntity.getCouponStatus())
          .usedAt(couponEntity.getUsedAt())
          .couponPolicy(CouponPolicy.from(couponEntity.getCouponPolicyEntity()))
          .build();
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CouponPolicy {
      private UUID id;
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
            .id(couponPolicyEntity.getId())
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
}
