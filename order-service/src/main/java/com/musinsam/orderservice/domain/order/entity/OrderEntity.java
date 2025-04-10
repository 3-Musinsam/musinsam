package com.musinsam.orderservice.domain.order.entity;

import com.musinsam.common.domain.BaseEntity;
import com.musinsam.orderservice.domain.order.vo.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "p_order")
@SQLRestriction("deleted_at IS NULL")
public class OrderEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private Long userId;

  private UUID paymentId;

  private UUID couponId;

  private BigDecimal totalAmount;

  private BigDecimal discountAmount;

  private BigDecimal finalAmount;

  private Integer totalQuantity;

  private String request;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  private String cancelReason;

  private ZonedDateTime canceledAt;
}
