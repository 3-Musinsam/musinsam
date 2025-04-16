package com.musinsam.couponservice.app.infrastructure.persistence.querydsl;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.CouponPolicySearchCondition;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.response.ResCouponPoliciesGetDtoApiV1;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.QCouponPolicyEntity;
import com.musinsam.couponservice.app.domain.repository.couponPolicy.CouponPolicyQueryRepository;
import com.musinsam.couponservice.app.presentation.util.querydsl.QuerydslCouponPolicySortUtil;
import com.musinsam.couponservice.app.presentation.util.querydsl.QuerydslUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponPolicyQueryRepositoryImpl implements CouponPolicyQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<ResCouponPoliciesGetDtoApiV1> findCouponPoliciesByCondition(CouponPolicySearchCondition condition,
                                                                          CurrentUserDtoApiV1 currentUser,
                                                                          Pageable pageable) {
    QCouponPolicyEntity couponPolicy = QCouponPolicyEntity.couponPolicyEntity;

    BooleanBuilder builder = new BooleanBuilder()
        .and(QuerydslUtil.eqIfNotNull(couponPolicy.id, condition.getCouponPolicyId()))
        .and(QuerydslUtil.likeContains(couponPolicy.name, condition.getCouponPolicyName()))
        .and(QuerydslUtil.likeContains(couponPolicy.description, condition.getCouponPolicyDescription()))
        .and(QuerydslUtil.eqIfNotNull(couponPolicy.discountType, condition.getDiscountType()))
        .and(QuerydslUtil.eqIfNotNull(couponPolicy.companyId, condition.getCompanyId()))
        .and(
            QuerydslUtil.betweenIfNotNull(couponPolicy.startedAt, condition.getStartedFrom(), condition.getStartedTo()))
        .and(QuerydslUtil.betweenIfNotNull(couponPolicy.endedAt, condition.getEndedFrom(), condition.getEndedTo()))
        .and(QuerydslUtil.betweenIfNotNull(couponPolicy.createdAt, condition.getCreatedFrom(),
            condition.getCreatedTo()));

    List<OrderSpecifier<?>> orderSpecifiers = QuerydslCouponPolicySortUtil.toOrderSpecifiers(pageable.getSort());

    List<ResCouponPoliciesGetDtoApiV1> content = queryFactory
        .select(Projections.constructor(ResCouponPoliciesGetDtoApiV1.class,
            couponPolicy.id,
            couponPolicy.name,
            couponPolicy.description,
            couponPolicy.discountType,
            couponPolicy.discountValue,
            couponPolicy.minimumOrderAmount,
            couponPolicy.maximumDiscountAmount,
            couponPolicy.totalQuantity,
            couponPolicy.startedAt,
            couponPolicy.endedAt,
            couponPolicy.companyId,
            couponPolicy.createdAt,
            couponPolicy.createdBy,
            couponPolicy.updatedAt,
            couponPolicy.updatedBy,
            couponPolicy.deletedAt,
            couponPolicy.deletedBy
        ))
        .from(couponPolicy)
        .where(builder)
        .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();


    Long total = queryFactory
        .select(couponPolicy.count())
        .from(couponPolicy)
        .where(builder)
        .fetchOne();

    return new PageImpl<>(content, pageable, total != null ? total : 0);
  }

}
