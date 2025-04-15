package com.musinsam.eventservice.domain.event.entity;

import com.musinsam.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "p_event_product")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class EventProductEntity extends BaseEntity {

  @Id
  @UuidGenerator
  private UUID id;

  @Column(nullable = false)
  private UUID productId;

  @Column(nullable = false)
  private BigDecimal salePrice;

  @Column(nullable = false)
  private BigDecimal originPrice;

  @Column
  private Integer discountRate;

  @Column
  private Integer maxPurchase;

  @Column(nullable = false)
  private Integer soldQuantity = 0;

  @ManyToOne
  @JoinColumn(name = "event_id")
  private EventEntity event;

}
