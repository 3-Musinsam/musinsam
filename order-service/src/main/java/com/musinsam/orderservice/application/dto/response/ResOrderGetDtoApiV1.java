package com.musinsam.orderservice.application.dto.response;

import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
      private String orderStatus;
      private BigDecimal totalAmount;
      private BigDecimal discountAmount;
      private BigDecimal finalAmount;
      private String firstProductName;
      private int productCount;
      private ZonedDateTime createdAt;

      public static List<Order> from(List<OrderEntity> orderEntities) {
        return orderEntities.stream()
            .map(Order::from)
            .collect(Collectors.toList());
      }

      public static Order from(OrderEntity orderEntity) {
        return Order.builder()
            // TODO:
            .build();
      }
    }
  }

  public static ResOrderGetDtoApiV1 of(Page<OrderEntity> orderEntityPage) {
    return ResOrderGetDtoApiV1.builder()
        .orderPage(new OrderPage(orderEntityPage))
        .build();
  }
}
