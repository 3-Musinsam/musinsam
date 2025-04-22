package com.musinsam.couponservice.app.application.service.v3.coupon;


import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_ERROR_UPDATE_STATE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.common.exception.CustomException;
import com.musinsam.couponservice.app.application.dto.v3.coupon.response.ResCouponIssueDtoApiV3;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("couponStateServiceV3")
public class CouponStateServiceImpl implements CouponStateService {

  private final RedissonClient redissonClient;
  private final ObjectMapper objectMapper;

  private static final String COUPON_STATE_KEY = "coupon:state:";

  /**
   * 쿠폰 상태 저장
   *
   * @param couponEntity 상태를 저장할 쿠폰
   */
  @Override
  public void updateCouponState(CouponEntity couponEntity) {
    try {
      String stateKey = COUPON_STATE_KEY + couponEntity.getId();
      String couponJson = objectMapper.writeValueAsString(
          ResCouponIssueDtoApiV3.from(couponEntity, couponEntity.getCouponPolicyEntity()));
      RBucket<String> bucket = redissonClient.getBucket(stateKey);
      bucket.set(couponJson);

      log.info("Coupon state updated: {}", couponEntity.getId());

    } catch (Exception e) {
      log.error("Error updating coupon state: {}", e.getMessage(), e);
      throw new CustomException(COUPON_ERROR_UPDATE_STATE);
    }
  }
}
