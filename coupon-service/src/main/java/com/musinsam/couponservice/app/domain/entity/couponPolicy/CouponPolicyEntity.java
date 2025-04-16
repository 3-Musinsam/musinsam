package com.musinsam.couponservice.app.domain.entity.couponPolicy;

import com.musinsam.common.domain.BaseEntity;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_coupon_policy")
@Entity
public class CouponPolicyEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "description", nullable = false, length = 255)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "discount_type", nullable = false)
  private DiscountType discountType;

  @Column(name = "discount_value", nullable = false)
  private Integer discountValue;

  @Column(name = "minimum_order_amount", nullable = false)
  private Integer minimumOrderAmount;

  @Column(name = "maximum_discount_amount", nullable = false)
  private Integer maximumDiscountAmount;

  @Column(name = "total_quantity", nullable = false)
  private Integer totalQuantity;

  @Column(name = "started_at", nullable = false)
  private ZonedDateTime startedAt;

  @Column(name = "ended_at", nullable = false)
  private ZonedDateTime endedAt;

  @Column(name = "seller_id", nullable = false)
  private UUID companyId;

  @Builder
  private CouponPolicyEntity(String name, String description, DiscountType discountType, Integer discountValue,
                             Integer minimumOrderAmount, Integer maximumDiscountAmount, Integer totalQuantity,
                             ZonedDateTime startedAt, ZonedDateTime endedAt, UUID companyId) {
    this.name = name;
    this.description = description;
    this.discountType = discountType;
    this.discountValue = discountValue;
    this.minimumOrderAmount = minimumOrderAmount;
    this.maximumDiscountAmount = maximumDiscountAmount;
    this.totalQuantity = totalQuantity;
    this.startedAt = startedAt;
    this.endedAt = endedAt;
    this.companyId = companyId;
  }

  public static CouponPolicyEntity of(String name,
                                      String description,
                                      DiscountType discountType,
                                      Integer discountValue,
                                      Integer minimumOrderAmount,
                                      Integer maximumDiscountAmount,
                                      Integer totalQuantity,
                                      ZonedDateTime startedAt,
                                      ZonedDateTime endedAt,
                                      UUID companyId) {
    return CouponPolicyEntity.builder()
        .name(name)
        .description(description)
        .discountType(discountType)
        .discountValue(discountValue)
        .minimumOrderAmount(minimumOrderAmount)
        .maximumDiscountAmount(maximumDiscountAmount)
        .totalQuantity(totalQuantity)
        .startedAt(startedAt)
        .endedAt(endedAt)
        .companyId(companyId)
        .build();
  }

  @Override
  public void softDelete(Long userId, ZoneId zoneId) {
    super.softDelete(userId, zoneId);
  }

}
