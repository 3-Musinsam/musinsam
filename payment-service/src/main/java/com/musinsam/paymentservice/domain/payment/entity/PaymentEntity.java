package com.musinsam.paymentservice.domain.payment.entity;

import com.musinsam.common.domain.BaseEntity;
import com.musinsam.paymentservice.domain.payment.vo.PaymentMethod;
import com.musinsam.paymentservice.domain.payment.vo.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "p_payments")
@SQLRestriction("deleted_at IS NULL")
public class PaymentEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "order_id")
  private UUID orderId;

  @Column(name = "provider_order_id")
  private String providerOrderId;

  @Column(name = "payment_key")
  private String paymentKey;

  @Column(name = "payment_provider")
  private String paymentProvider;

  @Column(name = "method_type")
  @Enumerated(EnumType.STRING)
  private PaymentMethod methodType;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "payment_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  @Column(name = "reason")
  private String reason;
}
