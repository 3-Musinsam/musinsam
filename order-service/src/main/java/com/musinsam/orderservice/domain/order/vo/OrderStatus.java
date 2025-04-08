package com.musinsam.orderservice.domain.order.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
  PENDING("주문 대기"),
  PAID("결제 완료"),
  COMPLETED("주문 처리 완료"),
  SHIPPING("배송 대기"),
  CANCELLED("주문 취소");

  private final String description;
}
