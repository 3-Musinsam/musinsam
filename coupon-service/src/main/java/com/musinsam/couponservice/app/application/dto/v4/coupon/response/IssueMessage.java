package com.musinsam.couponservice.app.application.dto.v4.coupon.response;

import com.musinsam.common.user.UserRoleType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record IssueMessage(
    UUID couponPolicyId,
    Long userId,
    UserRoleType userRoleType
) {
}
