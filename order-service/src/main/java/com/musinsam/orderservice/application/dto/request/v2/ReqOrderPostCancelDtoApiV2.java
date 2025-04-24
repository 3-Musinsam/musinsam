package com.musinsam.orderservice.application.dto.request.v2;

import com.musinsam.orderservice.application.dto.request.ReqOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.domain.order.vo.OrderCancelType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqOrderPostCancelDtoApiV2 {

  @Valid
  @NotNull(message = "주문 정보가 존재하지 않습니다.")
  private ReqOrderPostCancelDtoApiV1.Order order;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Order {

    private String cancelReason;
    private OrderCancelType cancelType;
  }
}
