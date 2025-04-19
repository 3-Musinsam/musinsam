package com.musinsam.couponservice.app.application.service.v1.couponPolicy;

import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.DUPLICATED_COUPON_POLICY;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.INVALID_COMPANY_ID;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.NOT_FOUND_COUPON_POLICY;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.client.ShopClient;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.CouponPolicySearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.ReqCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPoliciesGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyGetDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyQueryRepository;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode;
import java.time.ZoneId;
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
public class CouponPolicyServiceImpl implements CouponPolicyService {


  private final CouponPolicyRepository couponPolicyRepository;
  private final CouponPolicyQueryRepository couponPolicyQueryRepository;
  private final ShopClient shopClient;

  private CouponPolicyEntity findCouponPolicyById(UUID id) {
    return couponPolicyRepository.findById(id)
        .orElseThrow(() -> new CustomException(NOT_FOUND_COUPON_POLICY));
  }

  private Boolean existsCouponPolicyByName(String name) {
    return couponPolicyRepository.existsCouponPolicyByName(name);
  }

  private void validateCompanyExists(UUID companyId) {
    if (companyId != null && !shopClient.existsShopById(companyId.toString())) {
      throw new CustomException(INVALID_COMPANY_ID);
    }
  }

  @Transactional
  @Override
  public ResCouponPolicyIssueDtoApiV1 issueCouponPolicy(
      ReqCouponPolicyIssueDtoApiV1 response,
      CurrentUserDtoApiV1 currentUser
  ) {
    if (existsCouponPolicyByName(response.getName())) {
      throw new CustomException(DUPLICATED_COUPON_POLICY);
    }

    // TODO: shop-service 에서 existsShopById 구현되면 사용
//    validateCompanyExists(response.getCompanyId());

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
        response.getCompanyId(),
        response.isLimitedIssue()
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

  @Transactional(readOnly = true)
  @Override
  public Page<ResCouponPoliciesGetDtoApiV1> getCouponPoliciesByCondition(
      CouponPolicySearchCondition condition,
      CurrentUserDtoApiV1 currentUser,
      Pageable pageable) {

    return couponPolicyQueryRepository.findCouponPoliciesByCondition(condition, currentUser, pageable);
  }

  @Transactional
  @Override
  public void deleteCouponPolicy(UUID id, CurrentUserDtoApiV1 currentUser) {
    CouponPolicyEntity couponPolicyEntity = findCouponPolicyById(id);

    couponPolicyEntity.softDelete(currentUser.userId(), ZoneId.of("Asia/Seoul"));
    couponPolicyRepository.save(couponPolicyEntity);
  }


}
