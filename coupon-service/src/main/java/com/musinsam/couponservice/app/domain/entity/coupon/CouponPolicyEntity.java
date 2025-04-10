package com.musinsam.couponservice.app.domain.entity.coupon;

import com.musinsam.couponservice.app.domain.vo.DiscountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class CouponPolicyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
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

  @Column(name = "company_id", nullable = false)
  private UUID companyId;

  @Builder
  public CouponPolicyEntity(
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
      UUID companyId) {
    this.id = id;
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
}
