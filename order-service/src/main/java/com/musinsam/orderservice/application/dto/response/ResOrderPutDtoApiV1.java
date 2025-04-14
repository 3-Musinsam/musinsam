package com.musinsam.orderservice.application.dto.response;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.entity.OrderItemEntity;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
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
public class ResOrderPutDtoApiV1 {

  private Order order;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Order {

    private UUID id;
    private String orderStatus;
    private List<OrderItem> orderItems;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String request;
    private ZonedDateTime updatedAt;

    public static Order from(OrderEntity orderEntity) {
      List<OrderItem> orderItems = orderEntity.getOrderItems().stream()
          .map(OrderItem::from)
          .toList();

      return Order.builder()
          .id(orderEntity.getId())
          .orderItems(orderItems)
          .orderStatus(orderEntity.getOrderStatus().name())
          .totalAmount(orderEntity.getTotalAmount())
          .discountAmount(orderEntity.getDiscountAmount())
          .finalAmount(orderEntity.getFinalAmount())
          .request(orderEntity.getRequest())
          .updatedAt(orderEntity.getUpdatedAt())
          .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {

      private UUID productId;
      private String productName;
      private BigDecimal price;

      @NotNull(message = "상품 수량을 입력해주세요.")
      private Integer quantity;

      public static OrderItem from(OrderItemEntity orderItemEntity) {
        return OrderItem.builder()
            .productId(orderItemEntity.getId())
            .productName(orderItemEntity.getProductName())
            .quantity(orderItemEntity.getQuantity())
            .price(orderItemEntity.getPrice())
            .build();
      }
    }
  }

  public static ResOrderPutDtoApiV1 of(OrderEntity orderEntity) {
    return ResOrderPutDtoApiV1.builder()
        .order(Order.from(orderEntity))
        .build();
  }
}
