package com.musinsam.couponservice.app.application.service.v4;

import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_ALREADY_USED;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_ISSUE_TRY_AGAIN;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_NOT_FOUND;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.INVALID_COUPON_DATE;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.NOT_FOUND_COUPON;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.common.user.UserRoleType;
import com.musinsam.couponservice.app.application.dto.v3.coupon.request.ReqCouponUseDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.coupon.response.ResCouponUseDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.couponPolicy.response.ResCouponPolicyGetDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v4.coupon.request.ReqCouponIssueDtoApiV4;
import com.musinsam.couponservice.app.application.dto.v4.coupon.response.IssueMessage;
import com.musinsam.couponservice.app.application.service.v3.coupon.CouponStateService;
import com.musinsam.couponservice.app.application.service.v3.couponPolicy.CouponPolicyService;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponRepository;
import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import java.time.ZonedDateTime;
import java.util.UUID;
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
@Service("CouponServiceV4")
public class CouponServiceImpl implements CouponService {

  private static final String COUPON_QUANTITY_KEY = "coupon:quantity:";
  private static final String COUPON_LOCK_KEY = "coupon:lock:";
  private static final long LOCK_WAIT_TIME = 3L;
  private static final long LOCK_LEASE_TIME = 5L;

  private final RedissonClient redissonClient;
  private final CouponRepository couponRepository;
  private final CouponProducer couponProducer;
  private final CouponStateService couponStateService;
  private final CouponPolicyService couponPolicyService;

  private String generateCouponCode() {
    return UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
  }

  @Transactional(readOnly = true)
  @Override
  public void requestCouponIssue(ReqCouponIssueDtoApiV4 request, CurrentUserDtoApiV1 currentUser) {
    String quantityKey = COUPON_QUANTITY_KEY + request.couponPolicyId();
    String lockKey = COUPON_LOCK_KEY + request.couponPolicyId();
    RLock lock = redissonClient.getLock(lockKey);

    try {
      boolean isLocked = lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
      if (!isLocked) {
        throw new CustomException(COUPON_ISSUE_TRY_AGAIN);
      }

      ResCouponPolicyGetDtoApiV3 couponPolicyEntityDto = couponPolicyService.getCouponPolicy(request.couponPolicyId(),
          currentUser);

      if (couponPolicyEntityDto == null) {
        throw new CustomException(NOT_FOUND_COUPON);
      }

      ZonedDateTime now = ZonedDateTime.now();
      if (now.isBefore(couponPolicyEntityDto.startedAt()) || now.isAfter(couponPolicyEntityDto.endedAt())) {
        throw new CustomException(INVALID_COUPON_DATE);
      }

      // 수량 체크 및 감소
      RAtomicLong atomicQuantity = redissonClient.getAtomicLong(quantityKey);
      long remainingQuantity = atomicQuantity.decrementAndGet();

      if (remainingQuantity < 0) {
        atomicQuantity.incrementAndGet();
        throw new CustomException(COUPON_ALREADY_USED);
      }

      // Kafka로 쿠폰 발급 요청 전송
      couponProducer.sendCouponIssueRequest(
          IssueMessage.builder()
              .couponPolicyId(request.couponPolicyId())
              .userId(currentUser.userId())
              .userRoleType(currentUser.role())
              .build()
      );

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new CustomException(COUPON_ISSUE_TRY_AGAIN);
    } finally {
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
    }
  }

  @Transactional
  public void issueCoupon(IssueMessage message) {

    CurrentUserDtoApiV1 currentUser = new CurrentUserDtoApiV1(
        message.userId(),
        UserRoleType.valueOf(message.userRoleType().toString())
    );

    try {
      ResCouponPolicyGetDtoApiV3 couponPolicyEntityDto = couponPolicyService.getCouponPolicy(message.couponPolicyId(),
          currentUser);
      if (couponPolicyEntityDto == null) {
        throw new CustomException(NOT_FOUND_COUPON);
      }

      // 쿠폰 발급
      CouponEntity couponEntity = CouponEntity.of(
          couponPolicyEntityDto.toEntity(),
          currentUser.userId(),
          null,
          generateCouponCode(),
          CouponStatus.ISSUED,
          null
      );

      couponRepository.save(couponEntity);

      log.info("Coupon issued successfully: policyId={}, userId={}", message.couponPolicyId(), message.userId());

    } catch (Exception e) {
      log.error("Failed to issue coupon: {}", e.getMessage());
      throw e;
    }
  }

  @Override
  public ResCouponUseDtoApiV3 useCoupon(UUID couponId, ReqCouponUseDtoApiV3 request, CurrentUserDtoApiV1 currentUser) {
    CouponEntity couponEntity = couponRepository.findByIdWithLock(couponId)
        .orElseThrow(() -> new CustomException(COUPON_NOT_FOUND));

    couponEntity.useForV3(request.orderId());
    couponStateService.updateCouponState(couponEntity);

    return ResCouponUseDtoApiV3.from(couponEntity, couponEntity.getCouponPolicyEntity());
  }

}
