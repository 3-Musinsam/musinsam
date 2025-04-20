package com.musinsam.couponservice.app.application.service.v1.coupon;

import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_ALREADY_DELETED;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_ALREADY_ISSUED_BY_USER;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_ALREADY_USED;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_CODE_ALREADY_EXISTS;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_DOSE_NOT_BELONG_TO_USER;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.INVALID_COUPON_COMPANY_ID;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.INVALID_COUPON_DATE;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.INVALID_COUPON_MINIMUM_ORDER;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.NOT_FOUND_COUPON;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus.AVAILABLE;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus.CANCELLED;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus.ISSUED;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.COUPON_POLICY_ALREADY_DELETED;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.INVALID_DISCOUNT_VALUE;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.NOT_FOUND_COUPON_POLICY;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.CouponSearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponValidateDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResAvailableCouponDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponCancelDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponValidateDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponsGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResShopCouponDtoApiV1;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponQueryRepository;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponRepository;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.DiscountType;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {

  private final CouponRepository couponRepository;
  private final CouponPolicyRepository couponPolicyRepository;
  private final CouponQueryRepository couponQueryRepository;


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
    CouponPolicyEntity couponPolicy = findCouponPolicyById(request.getCouponPolicyId());

    String couponCode = couponPolicy.getId().toString();

    // 상태는 userId가 있으면 바로 ISSUED, 아니면 AVAILABLE
    CouponStatus status = request.getUserId() == null ? AVAILABLE : ISSUED;

    CouponEntity coupon = CouponEntity.of(
        couponPolicy,
        request.getUserId(), // 유저 있으면 바로 등록
        null,
        couponCode,
        status,
        null
    );

    CouponEntity saved = couponRepository.save(coupon);
    return ResCouponIssueDtoApiV1.from(saved, couponPolicy);
  }

  @Transactional
  @Override
  public ResCouponClaimDtoApiV1 claimCoupon(ReqCouponClaimDtoApiV1 request, CurrentUserDtoApiV1 currentUser) {
    CouponEntity couponEntity = findCouponById(request.getCouponId());

    boolean alreadyIssued = couponQueryRepository.
        existsByUserIdAndCouponPolicyId(
            currentUser.userId(),
            couponEntity.getCouponPolicyEntity().getId()
        );

    if (alreadyIssued) {
      throw new CustomException(COUPON_ALREADY_ISSUED_BY_USER);
    }

    couponEntity.claimCoupon(currentUser.userId());

    CouponEntity savedCouponEntity = couponRepository.save(couponEntity);
    CouponPolicyEntity couponPolicyEntity = savedCouponEntity.getCouponPolicyEntity();

    return ResCouponClaimDtoApiV1.from(savedCouponEntity, couponPolicyEntity);
  }

  @Transactional
  @Override
  public ResCouponUseDtoApiV1 useCoupon(UUID couponId, ReqCouponUseDtoApiV1 request, CurrentUserDtoApiV1 currentUser) {
    CouponEntity couponEntity = findCouponById(couponId);

    if (couponEntity.getDeletedAt() != null) {
      throw new CustomException(COUPON_ALREADY_DELETED);
    }
    if (!couponEntity.getUserId().equals(currentUser.userId())) {
      throw new CustomException(COUPON_DOSE_NOT_BELONG_TO_USER);
    }
    if (couponEntity.getCouponStatus() != ISSUED) {
      throw new CustomException(COUPON_ALREADY_USED);
    }

    CouponPolicyEntity policy = couponEntity.getCouponPolicyEntity();
    if (policy.getDeletedAt() != null) {
      throw new CustomException(COUPON_POLICY_ALREADY_DELETED);
    }

    ZonedDateTime now = ZonedDateTime.now();
    if (now.isBefore(policy.getStartedAt()) || now.isAfter(policy.getEndedAt())) {
      throw new CustomException(INVALID_COUPON_DATE);
    }

    couponEntity.markAsUsed(request.getOrderId());
    CouponEntity savedCouponEntity = couponRepository.save(couponEntity);

    return ResCouponUseDtoApiV1.from(savedCouponEntity, savedCouponEntity.getCouponPolicyEntity());
  }

  @Transactional(readOnly = true)
  @Override
  public Page<ResCouponsGetDtoApiV1> findCouponsByCondition(CouponSearchCondition condition,
                                                            CurrentUserDtoApiV1 currentUser, Pageable pageable) {
    return couponQueryRepository.findCouponsByCondition(condition, currentUser, pageable);
  }

  @Transactional(readOnly = true)
  @Override
  public ResCouponGetDtoApiV1 getCoupon(UUID couponId, CurrentUserDtoApiV1 currentUser) {
    CouponEntity couponEntity = findCouponById(couponId);
    CouponPolicyEntity couponPolicyById = findCouponPolicyById(couponEntity.getCouponPolicyEntity().getId());

    return ResCouponGetDtoApiV1.from(couponEntity, couponPolicyById);
  }

  @Transactional
  @Override
  public ResCouponCancelDtoApiV1 cancelCoupon(UUID couponId, CurrentUserDtoApiV1 currentUser) {
    CouponEntity couponEntity = findCouponById(couponId);
    couponEntity.updateCouponStatus(CANCELLED);

    CouponEntity savedCouponEntity = couponRepository.save(couponEntity);
    CouponPolicyEntity couponPolicyById = findCouponPolicyById(savedCouponEntity.getCouponPolicyEntity().getId());

    return ResCouponCancelDtoApiV1.from(savedCouponEntity, couponPolicyById);
  }

  @Transactional
  @Override
  public void deleteCoupon(UUID couponId, CurrentUserDtoApiV1 currentUser) {
    CouponEntity couponEntity = findCouponById(couponId);
    couponEntity.softDelete(currentUser.userId(), ZoneId.of("Asia/Seoul"));
    couponRepository.save(couponEntity);
  }

  @Override
  public ResShopCouponDtoApiV1 getCouponsByCompanyId(UUID shopId) {
    List<CouponEntity> coupons = couponRepository.findAllByCouponPolicyEntity_CompanyId(shopId);

    List<ResShopCouponDtoApiV1.Coupon> couponList = coupons.stream()
        .map(coupon -> ResShopCouponDtoApiV1.Coupon.builder()
            .couponId(coupon.getId())
            .couponName(coupon.getCouponPolicyEntity().getName())
            .couponPolicy(
                ResShopCouponDtoApiV1.Coupon.CouponPolicy.builder()
                    .startTime(coupon.getCouponPolicyEntity().getStartedAt())
                    .endTime(coupon.getCouponPolicyEntity().getEndedAt())
                    .build()
            )
            .build()
        ).toList();

    return ResShopCouponDtoApiV1.builder()
        .couponList(couponList)
        .build();
  }

  @Override
  public List<ResAvailableCouponDtoApiV1> getAvailableCoupons(Long userId, List<UUID> companyIds,
                                                              BigDecimal totalAmount) {
    ZonedDateTime now = ZonedDateTime.now();

    List<CouponEntity> coupons = couponQueryRepository.findAvailableCoupons(
        userId,
        companyIds,
        totalAmount,
        now);

    return coupons.stream()
        .map(coupon -> ResAvailableCouponDtoApiV1.from(coupon, coupon.getCouponPolicyEntity()))
        .toList();
  }

  @Override
  public ResCouponValidateDtoApiV1 validateCoupon(UUID couponId, ReqCouponValidateDtoApiV1 request) {
    CouponEntity couponEntity = findCouponById(couponId);

    if (!couponEntity.getUserId().equals(request.getUserId())) {
      throw new CustomException(COUPON_DOSE_NOT_BELONG_TO_USER);
    }
    if (couponEntity.getCouponStatus() != ISSUED) {
      throw new CustomException(COUPON_ALREADY_USED);
    }

    CouponPolicyEntity policy = couponEntity.getCouponPolicyEntity();
    ZonedDateTime now = ZonedDateTime.now();

    if (now.isBefore(policy.getStartedAt()) || now.isAfter(policy.getEndedAt())) {
      throw new CustomException(INVALID_COUPON_DATE);
    }
    if (!policy.getCompanyId().equals(request.getCompanyId())) {
      throw new CustomException(INVALID_COUPON_COMPANY_ID);
    }
    if (request.getTotalAmount().compareTo(BigDecimal.valueOf(policy.getMinimumOrderAmount())) < 0) {
      throw new CustomException(INVALID_COUPON_MINIMUM_ORDER);
    }

    int discount = calculateDiscount(policy, request.getTotalAmount());

    return ResCouponValidateDtoApiV1.builder()
        .couponId(couponId)
        .discountAmount(discount)
        .finalAmount(request.getTotalAmount().subtract(BigDecimal.valueOf(discount)))
        .build();
  }

  @Transactional
  @Override
  public void restoreCoupon(UUID couponId, CurrentUserDtoApiV1 currentUser) {
    CouponEntity coupon = findCouponById(couponId);

    if (!coupon.getUserId().equals(currentUser.userId())) {
      throw new CustomException(COUPON_DOSE_NOT_BELONG_TO_USER);
    }

    coupon.restore();
    couponRepository.save(coupon);
  }

  private int calculateDiscount(CouponPolicyEntity policy, BigDecimal totalAmount) {
    if (policy.getDiscountType() == DiscountType.FIXED_AMOUNT) {
      return policy.getDiscountValue();
    }

    if (policy.getDiscountType() == DiscountType.PERCENTAGE) {
      BigDecimal percent = BigDecimal.valueOf(policy.getDiscountValue()).divide(BigDecimal.valueOf(100));
      BigDecimal discount = totalAmount.multiply(percent);
      return Math.min(discount.intValue(), policy.getMaximumDiscountAmount());
    }

    throw new CustomException(INVALID_DISCOUNT_VALUE);
  }
}
