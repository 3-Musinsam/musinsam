package com.musinsam.orderservice.domain.order.repository;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository {

  OrderEntity save(OrderEntity orderEntity);

  Optional<OrderEntity> findByIdWithOrderItems(UUID orderId);

  Page<OrderEntity> findAll(Predicate searchPredicate, Pageable pageable);
}
