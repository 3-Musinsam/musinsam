package com.musinsam.couponservice.app.application.service.v1.coupon;

import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.*;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus.AVAILABLE;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus.ISSUED;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.NOT_FOUND_COUPON_POLICY;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponRepository;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  private CouponEntity findCouponById(UUID id) {
    return couponRepository.findById(id)
        .orElseThrow(() -> new CustomException(NOT_FOUND_COUPON));
  }

  private void existsByCouponCode(String couponCode) {
    if (couponRepository.existsCouponByCouponCode(couponCode)) {
      throw new CustomException(COUPON_CODE_ALREADY_EXISTS);
    }
  }

  @Transactional
  @Override
  public ResCouponIssueDtoApiV1 issueCoupon(ReqCouponIssueDtoApiV1 request, CurrentUserDtoApiV1 currentUser) {
    existsByCouponCode(request.getCouponCode());
    CouponPolicyEntity couponPolicyEntity = findCouponPolicyById(request.getCouponPolicyId());

    Long userId = request.getUserId() == null ? null : request.getUserId();

    CouponEntity couponEntity = CouponEntity.of(
        couponPolicyEntity,
        userId,
        null,
        request.getCouponCode(),
        userId == null ? AVAILABLE : ISSUED,
        null
    );

    CouponEntity save = couponRepository.save(couponEntity);

    return ResCouponIssueDtoApiV1.from(save);
  }

  @Transactional
  @Override
  public ResCouponClaimDtoApiV1 claimCoupon(ReqCouponClaimDtoApiV1 request, CurrentUserDtoApiV1 currentUser) {
    CouponEntity couponEntity = findCouponById(request.getCouponId());
    couponEntity.claimCoupon(currentUser.userId());
    couponEntity.updateCouponStatus(ISSUED);

    CouponEntity savedCouponEntity = couponRepository.save(couponEntity);
    log.info("savedCouponEntity.getCouponPolicyEntity().getId() : {}", savedCouponEntity.getCouponPolicyEntity().getId());
    CouponPolicyEntity couponPolicyById = findCouponPolicyById(savedCouponEntity.getCouponPolicyEntity().getId());

    return ResCouponClaimDtoApiV1.from(savedCouponEntity, couponPolicyById);
  }
}
