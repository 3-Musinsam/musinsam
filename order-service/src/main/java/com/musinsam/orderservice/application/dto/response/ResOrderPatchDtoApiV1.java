package com.musinsam.orderservice.application.dto.response;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 수정 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderPatchDtoApiV1 {

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Order {

    private UUID id;
    private String orderStatus;
  }

  // TODO:
  public static ResOrderPatchDtoApiV1 of() {
    return ResOrderPatchDtoApiV1.builder()
        .order(Order.builder()
            .build())
        .build();
  }

  // TODO:
  public static ResOrderPatchDtoApiV1 from(OrderEntity orderEntity) {
    return ResOrderPatchDtoApiV1.builder()
        .order(Order.builder()
            .build())
        .build();
  }

  private Order order;
}
