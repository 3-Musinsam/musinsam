package com.musinsam.orderservice.application.dto.request;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.entity.OrderItemEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 수정 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqOrderPutDtoApiV1 {

  @Valid
  @NotNull(message = "주문 정보가 존재하지 않습니다.")
  private Order order;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Order {

    private List<OrderItem> orderItems;
    private ShippingInfo shippingInfo;
    private String request;
    private UUID couponId;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;

    public void updateEntity(OrderEntity orderEntity) {
      List<OrderItemEntity> newOrderItemEntities = getOrderItems().stream()
          .map(item -> OrderItemEntity.builder()
              .productId(item.getId())
              .productName(item.getProductName())
              .price(item.getPrice())
              .quantity(item.getQuantity())
              .build()
          )
          .toList();

      orderEntity.getOrderItems().clear();

      orderEntity.update(
          totalAmount,
          discountAmount,
          finalAmount,
          request,
          couponId,
          newOrderItemEntities
      );
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {

      private UUID id;
      private String productName;
      private BigDecimal price;

      @NotNull(message = "상품 수량을 입력해주세요.")
      private Integer quantity;
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
  }
}
