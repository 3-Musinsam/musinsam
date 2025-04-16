package com.musinsam.couponservice.app.application.service.couponPolicy;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.CouponPolicySearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.ReqCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPoliciesGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyIssueDtoApiV1;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponPolicyService {
  ResCouponPolicyIssueDtoApiV1 issueCouponPolicy(ReqCouponPolicyIssueDtoApiV1 request, CurrentUserDtoApiV1 currentUser);

  ResCouponPolicyGetDtoApiV1 getCouponPolicy(UUID id, CurrentUserDtoApiV1 currentUser);

  Page<ResCouponPoliciesGetDtoApiV1> getCouponPoliciesByCondition(CouponPolicySearchCondition condition, CurrentUserDtoApiV1 currentUser, Pageable pageable);

  void deleteCouponPolicy(UUID id, CurrentUserDtoApiV1 currentUser);
}
