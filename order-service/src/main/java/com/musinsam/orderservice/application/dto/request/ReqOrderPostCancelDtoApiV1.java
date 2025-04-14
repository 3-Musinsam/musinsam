package com.musinsam.orderservice.application.dto.request;

import com.musinsam.orderservice.domain.order.vo.OrderCancelType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 취소 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqOrderPostCancelDtoApiV1 {

  @Valid
  @NotNull(message = "주문 정보가 존재하지 않습니다.")
  private Order order;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Order {

    private String cancelReason;
    private OrderCancelType cancelType;
  }
}
