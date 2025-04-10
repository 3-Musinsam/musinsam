package com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReqCouponPolicyGetDtoApiV1 {
  private UUID id;
}
