package com.musinsam.shopservice.domain.shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsam.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_shop")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @JsonProperty("user-id")
  @Column
  private Long userId;

  @Column(unique = true)
  private String name;

}
