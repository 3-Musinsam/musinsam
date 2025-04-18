package com.musinsam.shopservice.domain.shop.repository;

import static com.musinsam.shopservice.domain.shop.entity.QShopEntity.shopEntity;
import static com.musinsam.shopservice.infrastructure.config.QueryDslConfig.getUsableSize;

import com.musinsam.shopservice.application.dto.request.ReqShopGetSearchDtoApiV1;
import com.musinsam.shopservice.application.dto.request.ReqShopGetSearchDtoApiV1.Shop;
import com.musinsam.shopservice.domain.shop.entity.ShopEntity;
import com.musinsam.shopservice.infrastructure.config.QueryDslConfig;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShopQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public Page<ShopEntity> findShopList(Pageable pageable) {

    OrderSpecifier<?>[] orderSpecifiers = QueryDslConfig.getAllOrderSpecifierArr(pageable,
        shopEntity);

    List<ShopEntity> contents = jpaQueryFactory
        .select(shopEntity)
        .from(shopEntity)
        .orderBy(orderSpecifiers)
        .offset(pageable.getOffset())
        .limit(getUsableSize(pageable.getPageSize()))
        .fetch();

    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(shopEntity.count())
        .from(shopEntity);

    return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
  }

  public Page<ShopEntity> findShopSearchList(Pageable pageable,
      ReqShopGetSearchDtoApiV1.Shop shopDto) {

    int pageSize = validatePageSize(pageable.getPageSize());

    List<OrderSpecifier<?>> orderSpecifierList = dynamicOrder(pageable);

    List<ShopEntity> contents = jpaQueryFactory
        .selectFrom(shopEntity)
        .where(
            getShopForSearch(shopDto)
        )
        .orderBy(orderSpecifierList.toArray(new OrderSpecifier[0]))
        .offset(pageable.getOffset())
        .limit(pageSize)
        .fetch();

    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(shopEntity.count())
        .from(shopEntity)
        .where(
            getShopForSearch(shopDto)
        );

    return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
  }

  private Predicate getShopForSearch(Shop shop) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    if (shop.getId() != null) {
      booleanBuilder.and(shopEntity.id.eq(shop.getId()));
    }

    if (shop.getUserId() != null) {
      booleanBuilder.and(shopEntity.userId.eq(shop.getUserId()));
    }

    if (shop.getName() != null && !shop.getName().isEmpty()) {
      booleanBuilder.and(shopEntity.name.eq(shop.getName()));
    }

    return booleanBuilder;
  }

  private int validatePageSize(int pageSize) {
    return Set.of(10, 30, 50).contains(pageSize) ? pageSize : 10;
  }

  private <T> JPAQuery<T> query(Expression<T> expr) {
    return jpaQueryFactory
        .select(expr)
        .from(shopEntity);

  }

  private List<OrderSpecifier<?>> dynamicOrder(Pageable pageable) {
    List<OrderSpecifier<?>> orderSpecifierList = new ArrayList<>();

    if (pageable.getSort() != null) {
      for (Sort.Order sortOrder : pageable.getSort()) {
        Order direction = sortOrder.isAscending() ? Order.ASC : Order.DESC;

        switch (sortOrder.getProperty()) {
          case "createdAt":
            orderSpecifierList.add(new OrderSpecifier<>(direction, shopEntity.createdAt));
            break;
          case "updatedAt":
            orderSpecifierList.add(new OrderSpecifier<>(direction, shopEntity.updatedAt));
            break;
          default:
            orderSpecifierList.add(new OrderSpecifier<>(Order.ASC, shopEntity.createdAt));
        }
      }
    } else {
      orderSpecifierList.add(new OrderSpecifier<>(Order.ASC, shopEntity.createdAt));
    }
    return orderSpecifierList;
  }
}
