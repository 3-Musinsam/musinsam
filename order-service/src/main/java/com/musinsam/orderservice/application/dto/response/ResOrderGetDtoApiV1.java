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
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

/**
 * 주문 목록 조회 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderGetDtoApiV1 {

  private OrderPage orderPage;

  @Getter
  @ToString
  public static class OrderPage extends PagedModel<OrderPage.Order> {

    public OrderPage(Page<OrderEntity> orderEntityPage) {
      super(
          new PageImpl<>(
              Order.from(orderEntityPage.getContent()),
              orderEntityPage.getPageable(),
              orderEntityPage.getTotalElements()
          )
      );
    }

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
      private UUID couponId;
      private ZonedDateTime createdAt;
      private ZonedDateTime updatedAt;

      public static List<Order> from(List<OrderEntity> orderEntities) {
        return orderEntities.stream()
            .map(Order::from)
            .toList();
      }

      public static Order from(OrderEntity orderEntity) {
        List<OrderItem> orderItems = orderEntity.getOrderItems().stream()
            .map(OrderItem::from)
            .toList();

        return Order.builder()
            .id(orderEntity.getId())
            .userId(orderEntity.getUserId())
            .orderStatus(String.valueOf(orderEntity.getOrderStatus()))
            .orderItems(orderItems)
            .totalAmount(orderEntity.getTotalAmount())
            .discountAmount(orderEntity.getDiscountAmount())
            .finalAmount(orderEntity.getFinalAmount())
            .request(orderEntity.getRequest())
            .couponId(orderEntity.getCouponId())
            .createdAt(orderEntity.getCreatedAt())
            .updatedAt(orderEntity.getUpdatedAt())
            .build();
      }

      @Getter
      @Builder
      @NoArgsConstructor
      @AllArgsConstructor
      public static class OrderItem {

        private UUID id;
        private UUID productId;
        private String productName;
        private BigDecimal price;
        private Integer quantity;

        public static OrderItem from(OrderItemEntity orderItemEntity) {
          return OrderItem.builder()
              .id(orderItemEntity.getId())
              .productId(orderItemEntity.getProductId())
              .productName(orderItemEntity.getProductName())
              .quantity(orderItemEntity.getQuantity())
              .price(orderItemEntity.getPrice())
              .build();
        }
      }
    }
  }

  public static ResOrderGetDtoApiV1 of(Page<OrderEntity> orderEntityPage) {
    return ResOrderGetDtoApiV1.builder()
        .orderPage(new OrderPage(orderEntityPage))
        .build();
  }
}
