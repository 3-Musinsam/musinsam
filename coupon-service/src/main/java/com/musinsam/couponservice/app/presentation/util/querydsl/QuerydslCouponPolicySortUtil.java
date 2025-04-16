package com.musinsam.couponservice.app.presentation.util.querydsl;

import com.musinsam.couponservice.app.domain.entity.couponPolicy.CouponPolicyEntity;
import com.musinsam.couponservice.app.domain.entity.couponPolicy.QCouponPolicyEntity;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;

public class QuerydslCouponPolicySortUtil {

  private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
      "id", "name", "description", "discountType", "discountValue",
      "minimumOrderAmount", "maximumDiscountAmount", "totalQuantity",
      "startedAt", "endedAt", "companyId", "createdAt", "updatedAt", "deletedAt"
  );

  private QuerydslCouponPolicySortUtil() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<OrderSpecifier<?>> toOrderSpecifiers(Sort sort) {
    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

    QCouponPolicyEntity qEntity = QCouponPolicyEntity.couponPolicyEntity;
    PathBuilder<CouponPolicyEntity> entityPath = new PathBuilder<>(CouponPolicyEntity.class, qEntity.getMetadata());

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
