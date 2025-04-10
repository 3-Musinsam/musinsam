package com.musinsam.couponservice.app.doamin.entity.coupon;

import com.musinsam.common.domain.BaseEntity;
import com.musinsam.couponservice.app.doamin.vo.CouponStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_coupon")
@Entity
public class CouponEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coupon_policy_id", nullable = false)
  private CouponPolicyEntity couponPolicyEntity;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "order_id")
  private UUID orderId;

  @Column(name = "coupon_code", nullable = false)
  private String couponCode;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private CouponStatus couponStatus;

  @Column(name = "used_at")
  private ZonedDateTime usedAt;

  @Builder
  public CouponEntity(
      UUID id,
      CouponPolicyEntity couponPolicyEntity,
      Long userId,
      UUID orderId,
      String couponCode,
      CouponStatus couponStatus,
      ZonedDateTime usedAt) {
    this.id = id;
    this.couponPolicyEntity = couponPolicyEntity;
    this.userId = userId;
    this.orderId = orderId;
    this.couponCode = couponCode;
    this.couponStatus = couponStatus;
    this.usedAt = usedAt;
  }
}
