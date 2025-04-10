package com.musinsam.orderservice.infrastructure.persistence.order;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.repository.OrderRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID>, OrderRepository {

}
