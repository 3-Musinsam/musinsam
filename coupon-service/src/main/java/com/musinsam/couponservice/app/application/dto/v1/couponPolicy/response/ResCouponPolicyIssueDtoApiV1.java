package com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response;


import com.musinsam.couponservice.app.doamin.entity.coupon.CouponPolicyEntity;
import com.musinsam.couponservice.app.doamin.vo.DiscountType;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ResCouponPolicyIssueDtoApiV1 {

  private CouponPolicy couponPolicy;


  private static ResCouponPolicyIssueDtoApiV1 of(CouponPolicyEntity couponPolicyEntity) {
    return ResCouponPolicyIssueDtoApiV1.builder()
        .couponPolicy(CouponPolicy.from(couponPolicyEntity))
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
    private Integer totalQuantity;
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
          .totalQuantity(couponPolicyEntity.getTotalQuantity())
          .startedAt(couponPolicyEntity.getStartedAt())
          .endedAt(couponPolicyEntity.getEndedAt())
          .companyId(couponPolicyEntity.getCompanyId())
          .build();
    }
  }
}
