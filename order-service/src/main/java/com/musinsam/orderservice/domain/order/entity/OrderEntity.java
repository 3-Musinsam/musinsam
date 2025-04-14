package com.musinsam.orderservice.domain.order.entity;

import com.musinsam.common.domain.BaseEntity;
import com.musinsam.orderservice.domain.order.vo.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
  @Column(name = "id")
  private UUID id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "payment_id")
  private UUID paymentId;

  @Column(name = "coupon_id")
  private UUID couponId;

  @Column(name = "total_amount")
  private BigDecimal totalAmount;

  @Column(name = "discount_amount")
  private BigDecimal discountAmount;

  @Column(name = "final_amount")
  private BigDecimal finalAmount;

  @Column(name = "total_quantity")
  private Integer totalQuantity;

  @Column(name = "request")
  private String request;

  @Enumerated(EnumType.STRING)
  @Column(name = "order_status")
  private OrderStatus orderStatus;

  @Builder.Default
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<OrderItemEntity> orderItems = new ArrayList<>();
}
