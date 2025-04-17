package com.musinsam.orderservice.infrastructure.client.dto.response;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderClientUpdateOrderStatusDto {

  private String orderId;
  private String status;

  public static ResOrderClientUpdateOrderStatusDto of(OrderEntity orderEntity) {
    return ResOrderClientUpdateOrderStatusDto.builder()
        .orderId(String.valueOf(orderEntity.getId()))
        .status(String.valueOf(orderEntity.getOrderStatus()))
        .build();
  }
}
