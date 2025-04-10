package com.musinsam.couponservice.app.application.dto.v1.coupon.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReqCouponClaimDtoApiV1 {

  @NotNull(message = "Coupon Policy ID is required.")
  private UUID couponPolicyId;
}
