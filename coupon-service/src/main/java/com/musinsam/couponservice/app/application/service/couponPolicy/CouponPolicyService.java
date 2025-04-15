package com.musinsam.couponservice.app.application.service.couponPolicy;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.ReqCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyIssueDtoApiV1;
import java.util.UUID;

public interface CouponPolicyService {
  ResCouponPolicyIssueDtoApiV1 issueCouponPolicy(ReqCouponPolicyIssueDtoApiV1 request, CurrentUserDtoApiV1 currentUser);

  ResCouponPolicyGetDtoApiV1 getCouponPolicy(UUID id, CurrentUserDtoApiV1 currentUser);
}
