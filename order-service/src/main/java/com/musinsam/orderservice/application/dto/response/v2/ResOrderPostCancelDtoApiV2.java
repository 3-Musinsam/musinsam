package com.musinsam.orderservice.application.dto.response.v2;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.vo.OrderCancelType;
import com.musinsam.orderservice.domain.order.vo.OrderStatus;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderPostCancelDtoApiV2 {

  private Order order;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Order {

    private UUID id;
    private OrderStatus orderStatus;
    private ZonedDateTime canceledAt;
    private OrderCancelType orderCancelType;

    public static Order from(OrderEntity entity) {
      return Order.builder()
          .id(entity.getId())
          .orderStatus(entity.getOrderStatus())
          .canceledAt(entity.getCanceledAt())
          .orderCancelType(entity.getCancelType())
          .build();
    }
  }

  public static ResOrderPostCancelDtoApiV2 of(OrderEntity entity) {
    return ResOrderPostCancelDtoApiV2.builder()
        .order(Order.from(entity))
        .build();
  }
}
