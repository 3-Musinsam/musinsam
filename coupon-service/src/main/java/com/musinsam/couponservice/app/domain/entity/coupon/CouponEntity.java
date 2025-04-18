package com.musinsam.couponservice.app.domain.entity.coupon;

import com.musinsam.common.domain.BaseEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AccessLevel;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coupon_policy_id", nullable = false)
  private CouponPolicyEntity couponPolicyEntity;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "order_id")
  private UUID orderId;

  @Column(name = "coupon_code", nullable = false, unique = true)
  private String couponCode;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private CouponStatus couponStatus;

  @Column(name = "used_at")
  private ZonedDateTime usedAt;

  private CouponEntity(
      CouponPolicyEntity couponPolicyEntity,
      Long userId,
      UUID orderId,
      String couponCode,
      CouponStatus couponStatus,
      ZonedDateTime usedAt) {
    this.couponPolicyEntity = couponPolicyEntity;
    this.userId = userId;
    this.orderId = orderId;
    this.couponCode = couponCode;
    this.couponStatus = couponStatus;
    this.usedAt = usedAt;
  }

  public static CouponEntity of(
      CouponPolicyEntity couponPolicyEntity,
      Long userId,
      UUID orderId,
      String couponCode,
      CouponStatus couponStatus,
      ZonedDateTime usedAt) {
    return new CouponEntity(couponPolicyEntity, userId, orderId, couponCode, couponStatus, usedAt);
  }

  public void claimCoupon(Long userId) {
    this.userId = userId;
  }

  public void updateCouponStatus(CouponStatus couponStatus) {
    this.couponStatus = couponStatus;
  }

  public void updateUsedAt() {
    this.usedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
  }

  public void updateOrderId(UUID orderId) {
    this.orderId = orderId;
  }

  @Override
  public void softDelete(Long userId, ZoneId zoneId) {
    super.softDelete(userId, zoneId);
  }

}
