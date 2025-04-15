package com.musinsam.userservice.app.infrastructure.persistence.querydsl;

import com.musinsam.userservice.app.application.dto.v1.user.request.UserSearchCondition;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUsersGetDtoApiV1;
import com.musinsam.userservice.app.domain.user.entity.QUserEntity;
import com.musinsam.userservice.app.domain.user.repository.user.UserQueryRepository;
import com.musinsam.userservice.app.infrastructure.persistence.util.QuerydslSortUtil;
import com.musinsam.userservice.app.infrastructure.persistence.util.QuerydslUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<ResUsersGetDtoApiV1> findUsersByCondition(UserSearchCondition condition, Pageable pageable) {
    QUserEntity user = QUserEntity.userEntity;

    BooleanBuilder builder = new BooleanBuilder()
        .and(QuerydslUtil.eqIfNotNull(user.id, condition.getId()))
        .and(QuerydslUtil.likeContains(user.email, condition.getEmail()))
        .and(QuerydslUtil.likeContains(user.name, condition.getName()))
        .and(QuerydslUtil.eqIfNotNull(user.userRoleType, condition.getUserRoleType()))
        .and(QuerydslUtil.betweenIfNotNull(user.createdAt, condition.getCreatedFrom(), condition.getCreatedTo()))
        .and(QuerydslUtil.betweenIfNotNull(user.updatedAt, condition.getUpdatedFrom(), condition.getUpdatedTo()))
        .and(QuerydslUtil.betweenIfNotNull(user.deletedAt, condition.getDeletedFrom(), condition.getDeletedTo()))
        .and(QuerydslUtil.likeContains(Expressions.stringPath(user.createdBy.toString()), condition.getCreatedBy()))
        .and(QuerydslUtil.likeContains(Expressions.stringPath(user.updatedBy.toString()), condition.getUpdatedBy()))
        .and(QuerydslUtil.likeContains(Expressions.stringPath(user.deletedBy.toString()), condition.getDeletedBy()));

    List<ResUsersGetDtoApiV1> contents = queryFactory
        .select(Projections.constructor(
            ResUsersGetDtoApiV1.class,
            user.id,
            user.email,
            user.name,
            user.userRoleType,
            user.createdAt,
            user.createdBy,
            user.updatedAt,
            user.updatedBy,
            user.deletedAt,
            user.deletedBy
        ))
        .from(user)
        .where(builder)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(QuerydslSortUtil.toOrderSpecifiers(pageable.getSort()).toArray(new OrderSpecifier[0]))
        .fetch();

    Long total = queryFactory
        .select(user.count())
        .from(user)
        .where(builder)
        .fetchOne();

    return new PageImpl<>(contents, pageable, total != null ? total : 0);
  }
}