package com.musinsam.couponservice.app.application.service.couponPolicy;

import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.NOT_FOUND_COUPON_POLICY;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.ReqCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponPolicyServiceImpl implements CouponPolicyService {


  private final CouponPolicyRepository couponPolicyRepository;

  private CouponPolicyEntity findCouponPolicyById(Long id) {
    return couponPolicyRepository.findById(id)
        .orElseThrow(() -> new CustomException(NOT_FOUND_COUPON_POLICY));
  }

  private Boolean existsByCouponPolicyName(String name) {
    return couponPolicyRepository.existsByCouponCode(name);
  }

  @Override
  public ResCouponPolicyIssueDtoApiV1 issueCouponPolicy(
      ReqCouponPolicyIssueDtoApiV1 response,
      CurrentUserDtoApiV1 currentUser
  ) {
    if (existsByCouponPolicyName(response.getName())) {
      throw new CustomException(CouponPolicyErrorCode.DUPLICATED_COUPON_POLICY);
    }

    CouponPolicyEntity couponPolicyEntity = CouponPolicyEntity.of(
        response.getName(),
        response.getDescription(),
        response.getDiscountType(),
        response.getDiscountValue(),
        response.getMinimumOrderAmount(),
        response.getMaximumDiscountAmount(),
        response.getTotalQuantity(),
        response.getStartedAt(),
        response.getEndedAt(),
        response.getSellerId()
    );

    return ResCouponPolicyIssueDtoApiV1.from(couponPolicyEntity);
  }
}
