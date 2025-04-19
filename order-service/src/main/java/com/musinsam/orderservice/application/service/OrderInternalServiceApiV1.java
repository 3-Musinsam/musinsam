package com.musinsam.orderservice.application.service;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.exception.OrderException;
import com.musinsam.orderservice.domain.order.repository.OrderRepository;
import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import com.musinsam.orderservice.domain.order.vo.OrderStatus;
import com.musinsam.orderservice.infrastructure.client.dto.request.ReqOrderClientUpdateOrderStatusDto;
import com.musinsam.orderservice.infrastructure.client.dto.response.ResOrderClientGetByIdDto;
import com.musinsam.orderservice.infrastructure.client.dto.response.ResOrderClientUpdateOrderStatusDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderInternalServiceApiV1 {

  private final OrderRepository orderRepository;

  @Transactional
  public ResOrderClientGetByIdDto getOrderClientGetById(UUID orderId) {
    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));
    return ResOrderClientGetByIdDto.of(orderEntity);
  }

  @Transactional
  public ResOrderClientUpdateOrderStatusDto updateOrderStatus(UUID orderId,
      ReqOrderClientUpdateOrderStatusDto reqDto) {
    OrderEntity orderEntity = orderRepository.findByIdWithOrderItems(orderId)
        .orElseThrow(() -> OrderException.from(OrderErrorCode.ORDER_NOT_FOUND));

    orderEntity.updateOrderStatus(
        OrderStatus.valueOf(reqDto.getStatus()),
        reqDto.getReason()
    );

    orderRepository.save(orderEntity);

    return ResOrderClientUpdateOrderStatusDto.of(orderEntity);
  }
}
