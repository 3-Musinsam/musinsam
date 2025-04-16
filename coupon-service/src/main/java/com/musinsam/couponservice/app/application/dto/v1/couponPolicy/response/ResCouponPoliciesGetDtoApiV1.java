package com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response;

import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResCouponPoliciesGetDtoApiV1 {
  private UUID id;
  private String name;
  private String description;
  private DiscountType discountType;
  private Integer discountValue;
  private Integer minimumOrderAmount;
  private Integer maximumDiscountAmount;
  private Integer totalQuantity;
  private ZonedDateTime startedAt;
  private ZonedDateTime endedAt;
  private UUID companyId;
  private ZonedDateTime createdAt;
  private Long createdBy;
  private ZonedDateTime updatedAt;
  private Long updatedBy;
  private ZonedDateTime deletedAt;
  private Long deletedBy;
}
