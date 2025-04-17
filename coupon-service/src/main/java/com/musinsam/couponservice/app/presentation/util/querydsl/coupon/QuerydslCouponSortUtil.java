package com.musinsam.couponservice.app.presentation.util.querydsl.coupon;

import com.musinsam.couponservice.app.domain.entity.coupon.CouponEntity;
import com.musinsam.couponservice.app.domain.entity.coupon.QCouponEntity;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;

public class QuerydslCouponSortUtil {

  private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
      "id", "userId", "orderId", "couponCode", "couponStatus",
      "usedAt", "createdAt", "updatedAt", "deletedAt"
  );

  private QuerydslCouponSortUtil() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<OrderSpecifier<?>> toOrderSpecifiers(Sort sort) {
    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

    QCouponEntity qEntity = QCouponEntity.couponEntity;
    PathBuilder<CouponEntity> entityPath = new PathBuilder<>(CouponEntity.class, qEntity.getMetadata());

    for (Sort.Order order : sort) {
      String property = order.getProperty();
      if (!ALLOWED_SORT_FIELDS.contains(property)) continue;

      Order direction = order.isAscending() ? Order.ASC : Order.DESC;

      Expression<?> expression = entityPath.get(property);
      orderSpecifiers.add(new OrderSpecifier(direction, expression));
    }

    return orderSpecifiers;
  }
}