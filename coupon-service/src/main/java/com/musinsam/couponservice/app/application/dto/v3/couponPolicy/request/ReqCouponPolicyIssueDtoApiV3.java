package com.musinsam.couponservice.app.application.dto.v3.couponPolicy.request;

import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;

public record ReqCouponPolicyIssueDtoApiV3(

    @NotBlank(message = "Coupon policy name is required.")
    @Size(max = 100, message = "Coupon policy name must be at most 100 characters.")
    String name,

    @Size(max = 255, message = "Description must be at most 255 characters.")
    String description,

    @NotNull(message = "Discount type is required.")
    DiscountType discountType,

    @NotNull(message = "Discount value is required.")
    Integer discountValue,

    @NotNull(message = "Minimum order amount is required.")
    @Positive(message = "Minimum order amount must be greater than 0.")
    Integer minimumOrderAmount,

    @Positive(message = "Maximum discount amount must be greater than 0.")
    Integer maximumDiscountAmount,

    @NotNull(message = "Total quantity is required.")
    @Positive(message = "Total quantity must be greater than 0.")
    Integer totalQuantity,

    @NotNull(message = "Start date and time is required.")
    @Future(message = "Start date and time must be in the future.")
    ZonedDateTime startedAt,

    @NotNull(message = "End date and time is required.")
    @Future(message = "End date and time must be in the future.")
    ZonedDateTime endedAt,

    @NotNull(message = "Seller ID is required.")
    UUID companyId,

    @NotNull
    boolean limitedIssue
) {

  public static CouponPolicyEntity toEntity(ReqCouponPolicyIssueDtoApiV3 request) {
    return CouponPolicyEntity.builder()
        .name(request.name)
        .description(request.description)
        .discountType(request.discountType)
        .discountValue(request.discountValue)
        .minimumOrderAmount(request.minimumOrderAmount)
        .maximumDiscountAmount(request.maximumDiscountAmount)
        .totalQuantity(request.totalQuantity)
        .startedAt(request.startedAt)
        .endedAt(request.endedAt)
        .companyId(request.companyId)
        .limitedIssue(request.limitedIssue)
        .build();
  }
}
