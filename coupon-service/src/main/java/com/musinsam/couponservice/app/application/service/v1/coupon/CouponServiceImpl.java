package com.musinsam.couponservice.app.application.service.v1.coupon;

import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.COUPON_CODE_ALREADY_EXISTS;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponErrorCode.NOT_FOUND_COUPON;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus.AVAILABLE;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus.CANCELLED;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus.ISSUED;
import static com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus.USED;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.NOT_FOUND_COUPON_POLICY;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.CouponSearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponCancelDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponUseDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponsGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResShopCouponDtoApiV1;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponQueryRepository;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponRepository;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import java.time.ZoneId;
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
    CouponPolicyEntity couponPolicyById = findCouponPolicyById(savedCouponEntity.getCouponPolicyEntity().getId());

    return ResCouponClaimDtoApiV1.from(savedCouponEntity, couponPolicyById);
  }

  @Transactional
  @Override
  public ResCouponUseDtoApiV1 useCoupon(UUID couponId, ReqCouponUseDtoApiV1 request, CurrentUserDtoApiV1 currentUser) {
    CouponEntity couponEntity = findCouponById(couponId);
    couponEntity.updateUsedAt();
    couponEntity.updateCouponStatus(USED);
    couponEntity.updateOrderId(request.getOrderId());

    CouponEntity savedCouponEntity = couponRepository.save(couponEntity);
    CouponPolicyEntity couponPolicyById = findCouponPolicyById(savedCouponEntity.getCouponPolicyEntity().getId());

    return ResCouponUseDtoApiV1.from(savedCouponEntity, couponPolicyById);
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
}
