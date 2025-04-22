package com.musinsam.couponservice.app.application.service.v3.couponPolicy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v3.couponPolicy.request.ReqCouponPolicyIssueDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.couponPolicy.response.ResCouponPolicyGetDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.couponPolicy.response.ResCouponPolicyIssueDtoApiV3;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CouponPolicyService {

  ResCouponPolicyIssueDtoApiV3 issueCouponPolicy(ReqCouponPolicyIssueDtoApiV3 request, CurrentUserDtoApiV1 currentUser)
      throws JsonProcessingException;

  ResCouponPolicyGetDtoApiV3 getCouponPolicy(UUID id, CurrentUserDtoApiV1 currentUser);

  List<ResCouponPolicyGetDtoApiV3> getAllCouponPolicies();
}
