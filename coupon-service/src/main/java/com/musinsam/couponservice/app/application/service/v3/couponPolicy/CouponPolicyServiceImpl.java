package com.musinsam.couponservice.app.application.service.v3.couponPolicy;

import static com.musinsam.couponservice.app.application.dto.v3.couponPolicy.request.ReqCouponPolicyIssueDtoApiV3.toEntity;
import static com.musinsam.couponservice.app.application.dto.v3.couponPolicy.response.ResCouponPolicyIssueDtoApiV3.from;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.DUPLICATED_COUPON_POLICY;
import static com.musinsam.couponservice.app.domain.vo.couponPolicy.CouponPolicyErrorCode.NOT_FOUND_COUPON_POLICY;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v3.couponPolicy.request.ReqCouponPolicyIssueDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.couponPolicy.response.ResCouponPolicyGetDtoApiV3;
import com.musinsam.couponservice.app.application.dto.v3.couponPolicy.response.ResCouponPolicyIssueDtoApiV3;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service("couponPolicyServiceV3")
public class CouponPolicyServiceImpl implements CouponPolicyService {

  private final CouponPolicyRepository couponPolicyRepository;
  private final RedissonClient redissonClient;
  private final ObjectMapper objectMapper;

  private static final String COUPON_QUANTITY_KEY = "coupon:quantity:";
  private static final String COUPON_POLICY_KEY = "coupon:policy:";

  @Transactional
  @Override
  public ResCouponPolicyIssueDtoApiV3 issueCouponPolicy(ReqCouponPolicyIssueDtoApiV3 request,
                                                        CurrentUserDtoApiV1 currentUser)
      throws JsonProcessingException {

    if (couponPolicyRepository.existsCouponPolicyByName(request.name())) {
      throw new CustomException(DUPLICATED_COUPON_POLICY);
    }

    CouponPolicyEntity couponPolicy = toEntity(request);
    CouponPolicyEntity savedCouponPolicy = couponPolicyRepository.save(couponPolicy);

    // 초기 쿠폰 수량 설정
    String quantityKey = COUPON_QUANTITY_KEY + savedCouponPolicy.getId();
    RAtomicLong atomicQuantity = redissonClient.getAtomicLong(quantityKey);
    atomicQuantity.set(savedCouponPolicy.getTotalQuantity());

    ResCouponPolicyIssueDtoApiV3 fromCouponPolicy = from(savedCouponPolicy);

    // 쿠폰 정책 정보를 저장
    String policyKey = COUPON_POLICY_KEY + savedCouponPolicy.getId();
    String policyJson = objectMapper.writeValueAsString(fromCouponPolicy);
    RBucket<String> bucket = redissonClient.getBucket(policyKey);
    bucket.set(policyJson);

    return fromCouponPolicy;
  }



  @Transactional(readOnly = true)
  @Override
  public ResCouponPolicyGetDtoApiV3 getCouponPolicy(UUID id, CurrentUserDtoApiV1 currentUser) {
    // 캐시 우선 조회(Caching Read-Through) 패턴 적용
    String policyKey = COUPON_POLICY_KEY + id;
    RBucket<String> bucket = redissonClient.getBucket(policyKey);
    String policyJson = bucket.get();

    if (policyJson != null) {
      try {
        return objectMapper.readValue(policyJson, ResCouponPolicyGetDtoApiV3.class);
      } catch (JsonProcessingException e) {
        log.error("An error occurred while parsing coupon policy information into JSON", e);
      }
    }

    CouponPolicyEntity couponPolicyEntity = couponPolicyRepository.findById(id)
        .orElseThrow(() -> new CustomException(NOT_FOUND_COUPON_POLICY));

    return ResCouponPolicyGetDtoApiV3.from(couponPolicyEntity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ResCouponPolicyGetDtoApiV3> getAllCouponPolicies() {
    return couponPolicyRepository.findAll().stream()
        .map(ResCouponPolicyGetDtoApiV3::from)
        .collect(Collectors.toList());
  }
}

