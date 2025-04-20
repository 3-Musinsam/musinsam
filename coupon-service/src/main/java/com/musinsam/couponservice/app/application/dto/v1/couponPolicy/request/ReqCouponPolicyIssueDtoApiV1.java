package com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request;

import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReqCouponPolicyIssueDtoApiV1 {

  @NotBlank(message = "Coupon policy name is required.")
  @Size(max = 100, message = "Coupon policy name must be at most 100 characters.")
  private String name;

  @Size(max = 255, message = "Description must be at most 255 characters.")
  private String description;

  @NotNull(message = "Discount type is required.")
  private DiscountType discountType;

  @NotNull(message = "Discount value is required.")
  private Integer discountValue;

  @NotNull(message = "Minimum order amount is required.")
  @Positive(message = "Minimum order amount must be greater than 0.")
  private Integer minimumOrderAmount;

  @Positive(message = "Maximum discount amount must be greater than 0.")
  private Integer maximumDiscountAmount;

  @NotNull(message = "Total quantity is required.")
  @Positive(message = "Total quantity must be greater than 0.")
  private Integer totalQuantity;

  @NotNull(message = "Start date and time is required.")
  @Future(message = "Start date and time must be in the future.")
  private ZonedDateTime startedAt;

  @NotNull(message = "End date and time is required.")
  @Future(message = "End date and time must be in the future.")
  private ZonedDateTime endedAt;

  @NotNull(message = "Seller ID is required.")
  private UUID companyId;

  @NotNull
  private boolean limitedIssue;
}