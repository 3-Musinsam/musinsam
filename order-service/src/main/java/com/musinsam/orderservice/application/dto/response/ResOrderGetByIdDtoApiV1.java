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
 * 주문 상세 조회 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderGetByIdDtoApiV1 {

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
    private UUID couponId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static Order from(OrderEntity orderEntity) {
      List<OrderItem> orderItems = orderEntity.getOrderItems().stream()
          .map(OrderItem::from)
          .toList();

      return Order.builder()
          .id(orderEntity.getId())
          .userId(orderEntity.getUserId())
          .orderStatus(orderEntity.getOrderStatus().name())
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

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShippingInfo {

      private String receiverName;
      private String receiverPhone;
      private String zipCode;
      private String address;
      private String addressDetail;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentInfo {

      private UUID id;
      private String paymentStatus;
      private String paymentMethod;
      private BigDecimal totalAmount;
      private BigDecimal discountAmount;
      private BigDecimal finalAmount;
      private CouponInfo coupon;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CouponInfo {

      private UUID id;
      private String name;
      private BigDecimal discountAmount;
    }
  }

  public static ResOrderGetByIdDtoApiV1 of(OrderEntity orderEntity) {
    return ResOrderGetByIdDtoApiV1.builder()
        .order(Order.from(orderEntity))
        .build();
  }
}
