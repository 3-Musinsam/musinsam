package com.musinsam.orderservice.application.dto.response;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 취소 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderPostCancelDtoApiV1 {

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Order {

    private UUID id;
    private String orderStatus;
    private String cancelReason;
    private ZonedDateTime canceledAt;
  }

  // TODO:
  public static ResOrderPostCancelDtoApiV1 of() {
    return ResOrderPostCancelDtoApiV1.builder()
        .order(Order.builder()
            .build())
        .build();
  }

  // TODO:
  public static ResOrderPostCancelDtoApiV1 from(OrderEntity orderEntity) {
    return ResOrderPostCancelDtoApiV1.builder()
        .order(Order.builder()
            .build())
        .build();
  }

  private Order order;
}
