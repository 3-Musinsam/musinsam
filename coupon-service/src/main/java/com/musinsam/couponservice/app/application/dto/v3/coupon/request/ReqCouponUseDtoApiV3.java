package com.musinsam.couponservice.app.application.dto.v3.coupon.request;

import java.util.UUID;

public record ReqCouponUseDtoApiV3(
    UUID orderId
) {
}
