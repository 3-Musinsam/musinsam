package com.musinsam.orderservice.application.dto.request;

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
 * 주문 생성 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqOrderPostDtoApiV1 {

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
