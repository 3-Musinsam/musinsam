package com.musinsam.couponservice.app.infrastructure.persistence.querydsl.coupon;

import static com.musinsam.couponservice.app.presentation.util.querydsl.QuerydslUtil.betweenIfNotNull;
import static com.musinsam.couponservice.app.presentation.util.querydsl.QuerydslUtil.eqIfNotNull;
import static com.musinsam.couponservice.app.presentation.util.querydsl.QuerydslUtil.likeContains;
import static com.musinsam.couponservice.app.presentation.util.querydsl.coupon.QuerydslCouponSortUtil.toOrderSpecifiers;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.CouponSearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.coupon.response.ResCouponsGetDtoApiV1;
import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.coupon.QCouponEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.QCouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.coupon.CouponQueryRepository;
import com.musinsam.couponservice.app.domain.vo.coupon.CouponStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CouponQueryRepositoryImpl implements CouponQueryRepository {

  private final JPAQueryFactory queryFactory;

  QCouponEntity coupon = QCouponEntity.couponEntity;
  QCouponPolicyEntity policy = QCouponPolicyEntity.couponPolicyEntity;

  @Override
  public Page<ResCouponsGetDtoApiV1> findCouponsByCondition(CouponSearchCondition condition,
                                                            CurrentUserDtoApiV1 currentUser,
                                                            Pageable pageable) {

    BooleanBuilder builder = new BooleanBuilder()
        .and(eqIfNotNull(coupon.id, condition.getCouponId()))
        .and(eqIfNotNull(coupon.userId, condition.getUserId()))
        .and(eqIfNotNull(coupon.orderId, condition.getOrderId()))
        .and(eqIfNotNull(coupon.couponCode, condition.getCouponCode()))
        .and(eqIfNotNull(coupon.couponStatus, condition.getCouponStatus()))
        .and(betweenIfNotNull(coupon.usedAt, condition.getUsedFrom(), condition.getUsedTo()))
        .and(betweenIfNotNull(coupon.createdAt, condition.getCreatedFrom(), condition.getCreatedTo()))
        .and(eqIfNotNull(policy.id, condition.getCouponPolicyId()))
        .and(eqIfNotNull(policy.discountType, condition.getDiscountType()))
        .and(eqIfNotNull(policy.companyId, condition.getCompanyId()))
        .and(likeContains(policy.name, condition.getPolicyName()));

    List<CouponEntity> content = queryFactory
        .selectFrom(coupon)
        .join(coupon.couponPolicyEntity, policy).fetchJoin()
        .where(builder)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(toOrderSpecifiers(pageable.getSort()).toArray(OrderSpecifier[]::new))
        .fetch();

    // 총 개수 쿼리
    JPAQuery<Long> countQuery = queryFactory
        .select(coupon.count())
        .from(coupon)
        .join(coupon.couponPolicyEntity, policy)
        .where(builder);

    // DTO 매핑
    List<ResCouponsGetDtoApiV1> dtoList = content.stream()
        .map(c -> ResCouponsGetDtoApiV1.from(c, c.getCouponPolicyEntity()))
        .toList();

    return PageableExecutionUtils.getPage(dtoList, pageable, countQuery::fetchOne);
  }

  private static BooleanExpression eqIfNotNullByCouponStatus(SimpleExpression<CouponStatus> field, CouponStatus value) {
    return value != null ? field.eq(value) : null;
  }

  @Override
  public List<CouponEntity> findAvailableCoupons(Long userId, List<UUID> companyIds, BigDecimal totalAmount,
                                                 ZonedDateTime now) {

    Integer orderAmount = totalAmount.intValue();

    return queryFactory.selectFrom(coupon)
        .join(coupon.couponPolicyEntity, policy).fetchJoin()
        .where(
            coupon.userId.eq(userId),
            eqIfNotNullByCouponStatus(coupon.couponStatus, CouponStatus.ISSUED),
            policy.companyId.in(companyIds),
            policy.startedAt.before(now),
            policy.endedAt.after(now),
            policy.minimumOrderAmount.loe(orderAmount)
        )
        .fetch();
  }

  @Override
  public boolean existsByUserIdAndCouponPolicyId(Long userId, UUID couponPolicyId) {
    return queryFactory.selectOne()
        .from(coupon)
        .where(
            coupon.userId.eq(userId),
            coupon.couponPolicyEntity.id.eq(couponPolicyId),
            coupon.couponStatus.eq(CouponStatus.ISSUED)
        )
        .fetchFirst() != null;
  }

}
