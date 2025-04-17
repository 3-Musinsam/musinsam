package com.musinsam.paymentservice.infrastructure.client.dto.response;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderClientGetByOrderIdDto {

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
    private String orderName;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String request;
    private UUID couponId;
    //  private PaymentInfo paymentInfo;
    //  private ShippingInfo shippingInfo;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

//    public static Order from(OrderEntity orderEntity) {
//      List<OrderItem> orderItems = orderEntity.getOrderItems().stream()
//          .map(OrderItem::from)
//          .toList();
//
//      return Order.builder()
//          .id(orderEntity.getId())
//          .userId(orderEntity.getUserId())
//          .orderStatus(orderEntity.getOrderStatus().name())
//          .orderItems(orderItems)
//          .totalAmount(orderEntity.getTotalAmount())
//          .discountAmount(orderEntity.getDiscountAmount())
//          .finalAmount(orderEntity.getFinalAmount())
//          .request(orderEntity.getRequest())
//          .couponId(orderEntity.getCouponId())
//          //.paymentInfo()
//          //.shippingInfo()
//          .createdAt(orderEntity.getCreatedAt())
//          .updatedAt(orderEntity.getUpdatedAt())
//          .build();
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

//      public static OrderItem from(OrderItemEntity orderItemEntity) {
//        return OrderItem.builder()
//            .id(orderItemEntity.getId())
//            .productId(orderItemEntity.getProductId())
//            .productName(orderItemEntity.getProductName())
//            .quantity(orderItemEntity.getQuantity())
//            .price(orderItemEntity.getPrice())
//            .build();
//      }
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

//      // TODO:
//      public static ShippingInfo from(OrderEntity orderEntity) {
//        return ShippingInfo.builder()
//            .build();
//      }
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

//      // TODO:
//      public static PaymentInfo from(OrderEntity orderEntity) {
//        return PaymentInfo.builder()
//            .build();
//      }
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CouponInfo {

    private UUID id;
    private String name;
    private BigDecimal discountAmount;

//      // TODO:
//      public static CouponInfo from(OrderEntity orderEntity) {
//        return CouponInfo.builder()
//            .build();
//      }
//  }
//}

//  public static OrderClientDto of(OrderEntity orderEntity) {
//    return OrderClientDto.builder()
//        .order(Order.from(orderEntity))
//        .build();
  }
}
