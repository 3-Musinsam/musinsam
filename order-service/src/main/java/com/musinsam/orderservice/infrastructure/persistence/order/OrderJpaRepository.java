package com.musinsam.orderservice.infrastructure.persistence.order;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.repository.OrderRepository;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends
    JpaRepository<OrderEntity, UUID>,
    OrderRepository,
    QuerydslPredicateExecutor<OrderEntity> {

  @Override
  Optional<OrderEntity> findById(UUID orderId);

  @Override
  @Query("SELECT o FROM OrderEntity o LEFT JOIN FETCH o.orderItems WHERE o.id = :orderId")
  Optional<OrderEntity> findByIdWithOrderItems(@Param("orderId") UUID orderId);

  @Override
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT o FROM OrderEntity o LEFT JOIN FETCH o.orderItems WHERE o.id = :orderId")
  Optional<OrderEntity> findByIdWithOrderItemsForUpdate(@Param("id") UUID orderId);
}
