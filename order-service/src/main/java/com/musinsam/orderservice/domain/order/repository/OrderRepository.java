package com.musinsam.orderservice.domain.order.repository;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;

public interface OrderRepository {

  OrderEntity save(OrderEntity orderEntity);
}
