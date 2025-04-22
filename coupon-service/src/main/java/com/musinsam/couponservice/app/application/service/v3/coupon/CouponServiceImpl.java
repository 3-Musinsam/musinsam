package com.musinsam.couponservice.app.application.service.v3.coupon;

import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_NOT_FOUND;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v3.coupon.request.ReqCouponIssueDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.coupon.response.ResCouponIssueDtoApiV3;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service("couponServiceV3")
public class CouponServiceImpl implements CouponService {

  private final CouponRepository couponRepository;
  private final CouponRedisService couponRedisService;
  private final CouponStateService couponStateService;

  @Transactional
  @Override
  public ResCouponIssueDtoApiV3 issueCoupon(ReqCouponIssueDtoApiV3 request, CurrentUserDtoApiV1 currentUser) {
    CouponEntity couponEntity = couponRedisService.issueCoupon(request, currentUser);
    couponStateService.updateCouponState(couponRepository.findById(couponEntity.getId())
        .orElseThrow(() -> new CustomException(COUPON_NOT_FOUND)));
    return ResCouponIssueDtoApiV3.from(couponEntity, couponEntity.getCouponPolicyEntity());
  }
}
