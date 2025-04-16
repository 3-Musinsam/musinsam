package com.musinsam.couponservice.app.presentation.util.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.function.Supplier;

public class QuerydslUtil {

  private QuerydslUtil() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static BooleanExpression safe(BooleanExpression expression) {
    return expression;
  }

  public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> expressionSupplier) {
    try {
      BooleanExpression expression = expressionSupplier.get();
      return expression != null ? new BooleanBuilder(expression) : new BooleanBuilder();
    } catch (Exception e) {
      return new BooleanBuilder();
    }
  }

  // 문자열 like 검색 (부분검색)
  public static BooleanExpression likeContains(StringPath path, String keyword) {
    return (keyword == null || keyword.isBlank()) ? null : path.containsIgnoreCase(keyword.trim());
  }

  // 문자열 eq (정확 일치)
  public static BooleanExpression eqIgnoreCase(StringPath path, String keyword) {
    return (keyword == null || keyword.isBlank()) ? null : path.equalsIgnoreCase(keyword.trim());
  }

  // eq (null-safe)
  public static <T> BooleanExpression eqIfNotNull(SimpleExpression<T> path, T value) {
    return value == null ? null : path.eq(value);
  }

  // boolean eq (null-safe)
  public static BooleanExpression eqIfNotNull(BooleanPath path, Boolean value) {
    return value == null ? null : path.eq(value);
  }

  // in 조건 (null-safe)
  public static <T> BooleanExpression inIfNotEmpty(SimpleExpression<T> path, Collection<T> values) {
    return (values == null || values.isEmpty()) ? null : path.in(values);
  }

  // isNotNull 체크
  public static BooleanExpression isNotNull(Expression<?> path) {
    return path != null ? ((SimpleExpression<?>) path).isNotNull() : null;
  }

  // isNull 체크
  public static BooleanExpression isNull(Expression<?> path) {
    return path != null ? ((SimpleExpression<?>) path).isNull() : null;
  }

  // 날짜 범위 조건
  public static BooleanExpression betweenIfNotNull(
      DateTimePath<ZonedDateTime> path,
      ZonedDateTime from,
      ZonedDateTime to) {
    if (from != null && to != null) { return path.between(from, to); }
    if (from != null) { return path.goe(from); }
    if (to != null) { return path.loe(to); }
    return null;
  }
}