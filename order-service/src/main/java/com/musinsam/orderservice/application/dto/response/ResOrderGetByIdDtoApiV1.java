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
    private UUID userId;
    private String orderStatus;
    private List<OrderItem> orderItems;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String request;
    private ShippingInfo shippingInfo;
    private PaymentInfo paymentInfo;
    private ZonedDateTime createdAt;

    // TODO:
    public static Order from(OrderEntity orderEntity) {
      return Order.builder()
          .build();
    }
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderItem {

    private UUID id;
    private UUID productId;
    private String productName;
    private String productImage;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;

    // TODO:
    public static OrderItem from(OrderItemEntity orderItemEntity) {
      return OrderItem.builder()
          .build();
    }
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ShippingInfo {

    private UUID id;
    private String shippingStatus;
    private String trackingNumber;
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

  // TODO:
  public static ResOrderGetByIdDtoApiV1 of(OrderEntity orderEntity) {
    return ResOrderGetByIdDtoApiV1.builder()
        .order(Order.from(orderEntity))
        .build();
  }
}
