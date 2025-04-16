package com.musinsam.couponservice.app.application.service.v1.coupon;

import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_ALREADY_USED;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus.AVAILABLE;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.NOT_FOUND_COUPON_POLICY;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponRepository;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {

  private final CouponRepository couponRepository;
  private final CouponPolicyRepository couponPolicyRepository;

  private CouponPolicyEntity findCouponPolicyById(UUID id) {
    return couponPolicyRepository.findById(id)
        .orElseThrow(() -> new CustomException(NOT_FOUND_COUPON_POLICY));
  }

  private CouponEntity findCouponByCouponCode(String couponCode) {
    return couponRepository.findByCouponCode(couponCode)
        .orElseThrow(() -> new CustomException(COUPON_ALREADY_USED));
  }

  @Override
  public ResCouponIssueDtoApiV1 issueCoupon(ReqCouponIssueDtoApiV1 request, CurrentUserDtoApiV1 currentUser) {
    CouponPolicyEntity couponPolicyEntity = findCouponPolicyById(request.getCouponPolicyId());

    CouponEntity couponEntity = CouponEntity.of(
        couponPolicyEntity,
        request.getUserId(),
        null,
        request.getCouponCode(),
        AVAILABLE,
        null
    );

    CouponEntity save = couponRepository.save(couponEntity);

    return ResCouponIssueDtoApiV1.from(save);
  }
}
