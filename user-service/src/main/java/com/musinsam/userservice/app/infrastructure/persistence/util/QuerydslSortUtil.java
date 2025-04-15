package com.musinsam.userservice.app.infrastructure.persistence.util;

import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;


public class QuerydslSortUtil {

  private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
      "id", "email", "name", "userRoleType", "createdAt", "updatedAt", "deletedAt", "password"
  );

  private QuerydslSortUtil() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static List<OrderSpecifier<?>> toOrderSpecifiers(Sort sort) {
    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

    @SuppressWarnings("rawtypes")
    PathBuilder entityPath = new PathBuilder(UserEntity.class, "userEntity");

    for (Sort.Order order : sort) {
      String property = order.getProperty();
      if (!ALLOWED_SORT_FIELDS.contains(property)) continue;

      Order direction = order.isAscending() ? Order.ASC : Order.DESC;

      Expression expression = entityPath.get(property);
      orderSpecifiers.add(new OrderSpecifier(direction, expression));
    }

    return orderSpecifiers;
  }


}