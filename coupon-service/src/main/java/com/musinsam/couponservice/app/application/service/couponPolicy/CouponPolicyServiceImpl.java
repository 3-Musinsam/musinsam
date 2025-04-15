package com.musinsam.couponservice.app.application.service.couponPolicy;

import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.NOT_FOUND_COUPON_POLICY;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.ReqCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponPolicyServiceImpl implements CouponPolicyService {


  private final CouponPolicyRepository couponPolicyRepository;

  private CouponPolicyEntity findCouponPolicyById(UUID id) {
    return couponPolicyRepository.findById(id)
        .orElseThrow(() -> new CustomException(NOT_FOUND_COUPON_POLICY));
  }

  private Boolean existsCouponPolicyByName(String name) {
    return couponPolicyRepository.existsCouponPolicyByName(name);
  }

  @Transactional
  @Override
  public ResCouponPolicyIssueDtoApiV1 issueCouponPolicy(
      ReqCouponPolicyIssueDtoApiV1 response,
      CurrentUserDtoApiV1 currentUser
  ) {
    if (existsCouponPolicyByName(response.getName())) {
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
        response.getCompanyId()
    );
    CouponPolicyEntity savedCouponPolicyEntity = couponPolicyRepository.save(couponPolicyEntity);

    return ResCouponPolicyIssueDtoApiV1.from(savedCouponPolicyEntity);
  }

  @Transactional(readOnly = true)
  @Override
  public ResCouponPolicyGetDtoApiV1 getCouponPolicy(UUID id, CurrentUserDtoApiV1 currentUser) {
    CouponPolicyEntity couponPolicyEntity = findCouponPolicyById(id);

    return ResCouponPolicyGetDtoApiV1.from(couponPolicyEntity);
  }


}
