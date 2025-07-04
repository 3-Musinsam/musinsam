package com.musinsam.productservice.domain.product.entity;

import com.musinsam.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "p_product_coupon")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCouponEntity extends BaseEntity {

  @Id
  @UuidGenerator
  private UUID id;

  @Column(nullable = false)
  private UUID couponId;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private ProductEntity product;

}
