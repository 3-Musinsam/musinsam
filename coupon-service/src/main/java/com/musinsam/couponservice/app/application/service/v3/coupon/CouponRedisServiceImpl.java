package com.musinsam.couponservice.app.application.service.v3.coupon;

import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_ALREADY_USED;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_ISSUE_TRY_AGAIN;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.INVALID_COUPON_DATE;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.NOT_FOUND_COUPON_POLICY;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v3.coupon.request.ReqCouponIssueDtoApiV3;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponRepository;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service("couponRedisServiceV3")
public class CouponRedisServiceImpl implements CouponRedisService {

  private final RedissonClient redissonClient;
  private final CouponRepository couponRepository;
  private final CouponPolicyRepository couponPolicyRepository;

  private static final String COUPON_QUANTITY_KEY = "coupon:quantity:";
  private static final String COUPON_LOCK_KEY = "coupon:lock:";
  private static final long LOCK_WAIT_TIME = 3;
  private static final long LOCK_LEASE_TIME = 5;

  @Transactional
  public CouponEntity issueCoupon(ReqCouponIssueDtoApiV3 request, CurrentUserDtoApiV1 currentUser) {
    String quantityKey = COUPON_QUANTITY_KEY + request.couponPolicyId();
    String lockKey = COUPON_LOCK_KEY + request.couponPolicyId();
    RLock lock = redissonClient.getLock(lockKey);

    try {
      boolean isLocked = lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
      if (!isLocked) {
        throw new CustomException(COUPON_ISSUE_TRY_AGAIN);
      }

      CouponPolicyEntity couponPolicyEntity = couponPolicyRepository.findById(request.couponPolicyId())
          .orElseThrow(() -> new CustomException(NOT_FOUND_COUPON_POLICY));

      ZonedDateTime now = ZonedDateTime.now();
      if (now.isBefore(couponPolicyEntity.getStartedAt()) || now.isAfter(couponPolicyEntity.getEndedAt())) {
        throw new CustomException(INVALID_COUPON_DATE);
      }

      // 수량 체크 및 감소
      RAtomicLong atomicQuantity = redissonClient.getAtomicLong(quantityKey);
      long remainingQuantity = atomicQuantity.decrementAndGet();

      if (remainingQuantity < 0) {
        atomicQuantity.incrementAndGet();
        throw new CustomException(COUPON_ALREADY_USED);
      }

      // 쿠폰 발급
      CouponEntity couponEntity = CouponEntity.of(
          couponPolicyEntity,
          currentUser.userId(),
          null,
          generateCouponCode(),
          CouponStatus.ISSUED,
          null
      );

      couponEntity = couponRepository.save(couponEntity);

      return couponEntity;


    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new CustomException(COUPON_ISSUE_TRY_AGAIN);
    } finally {
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
    }
  }

  private String generateCouponCode() {
    return java.util.UUID.randomUUID().toString().substring(0, 8);
  }
}
