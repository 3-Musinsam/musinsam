package com.musinsam.orderservice.application.dto.response;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.entity.OrderItemEntity;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 생성 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderPostDtoApiV1 {

  private Order order;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Order {

    private UUID id;
    private Long userId;
    private String orderStatus;
    private List<OrderItem> orderItems;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String request;
    private ZonedDateTime createdAt;

    public static Order from(OrderEntity orderEntity) {
      return Order.builder()
          .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {

      private UUID productId;
      private String productName;
      private Integer quantity;
      private BigDecimal price;
      private BigDecimal finalAmount;

      public static OrderItem from(OrderItemEntity orderItemEntity) {
        return OrderItem.builder()
            .build();
      }
    }
  }

  // TODO:
  public static ResOrderPostDtoApiV1 of() {
    return ResOrderPostDtoApiV1.builder()
        .order(Order.builder()
            .build()
        )
        .build();
  }

  // TODO:
  public static ResOrderPostDtoApiV1 from(OrderEntity orderEntity) {
    return ResOrderPostDtoApiV1.builder()
        .order(Order.from(orderEntity))
        .build();
  }
}
