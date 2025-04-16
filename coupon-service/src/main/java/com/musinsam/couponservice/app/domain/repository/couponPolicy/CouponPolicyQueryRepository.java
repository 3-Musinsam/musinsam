package com.musinsam.couponservice.app.domain.repository.couponPolicy;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.CouponPolicySearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPoliciesGetDtoApiV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponPolicyQueryRepository {
  Page<ResCouponPoliciesGetDtoApiV1> findCouponPoliciesByCondition(CouponPolicySearchCondition condition, CurrentUserDtoApiV1 currentUser, Pageable pageable);
}
