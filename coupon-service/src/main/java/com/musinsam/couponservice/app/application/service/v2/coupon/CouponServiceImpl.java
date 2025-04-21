package com.musinsam.couponservice.app.application.service.v2.coupon;

import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.INVALID_COUPON_DATE;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.NOT_FOUND_COUPON_POLICY;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v2.coupon.request.ReqCouponIssueDtoApiV2;
import com.musinsam.couponservice.app.application.dto.v2.coupon.response.ResCouponIssueDtoApiV2;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponRepository;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service("couponServiceV2")
public class CouponServiceImpl implements CouponService {

  private final CouponRepository couponRepository;
  private final CouponPolicyRepository couponPolicyRepository;

  private String generateCouponCode() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

  @Transactional
  @Override
  public ResCouponIssueDtoApiV2 issueCoupon(ReqCouponIssueDtoApiV2 request, CurrentUserDtoApiV1 currentUser) {
    CouponPolicyEntity couponPolicyEntity = couponPolicyRepository.findByIdWithPessimisticLock(
            request.getCouponPolicyId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_COUPON_POLICY));

    ZonedDateTime now = ZonedDateTime.now();
    if (now.isBefore(couponPolicyEntity.getStartedAt()) || now.isAfter(couponPolicyEntity.getEndedAt())) {
      throw new CustomException(INVALID_COUPON_DATE);
    }
    
    couponPolicyEntity.checkAndDecreaseQuantityIfLimited();

    CouponEntity couponEntity = CouponEntity.of(
        couponPolicyEntity,
        currentUser.userId(),
        null,
        generateCouponCode(),
        CouponStatus.ISSUED,
        null);

    CouponEntity savedCouponEntity = couponRepository.save(couponEntity);

    return ResCouponIssueDtoApiV2.from(savedCouponEntity, couponPolicyEntity);
  }
}
