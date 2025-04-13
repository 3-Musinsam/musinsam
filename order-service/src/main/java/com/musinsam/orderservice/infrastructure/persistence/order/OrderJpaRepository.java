package com.musinsam.orderservice.infrastructure.persistence.order;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.entity.QOrderEntity;
import com.musinsam.orderservice.domain.order.repository.OrderRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends
    JpaRepository<OrderEntity, UUID>,
    OrderRepository,
    QuerydslPredicateExecutor<OrderEntity>,
    QuerydslBinderCustomizer<QOrderEntity> {

  @Override
  Optional<OrderEntity> findById(UUID orderId);

  @Override
  @Query("SELECT o FROM OrderEntity o LEFT JOIN FETCH o.orderItems WHERE o.id = :orderId")
  Optional<OrderEntity> findByIdWithOrderItems(@Param("orderId") UUID orderId);

  @Override
  default void customize(QuerydslBindings bindings, @NotNull QOrderEntity qOrderEntity) {
    bindings.bind(String.class).all((StringPath path, Collection<? extends String> values) -> {
      List<String> valueList = new ArrayList<>(values.stream().map(String::trim).toList());

      if (valueList.isEmpty()) {
        return Optional.empty();
      }

      BooleanBuilder builder = new BooleanBuilder();

      for (String value : valueList) {
        builder.or(path.containsIgnoreCase(value));
      }

      return Optional.of(builder);
    });
  }
}
