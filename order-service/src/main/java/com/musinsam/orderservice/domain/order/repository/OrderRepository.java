package com.musinsam.orderservice.domain.order.repository;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

  OrderEntity save(OrderEntity orderEntity);

  Optional<OrderEntity> findByIdWithOrderItems(UUID orderId);
}
