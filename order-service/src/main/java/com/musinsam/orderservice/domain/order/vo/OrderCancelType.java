package com.musinsam.orderservice.domain.order.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderCancelType {
  CUSTOMER_REQUEST("고객 변심"),
  SOLD_OUT("상품 품절"),
  DELIVERY_DELAY("배송 지연"),
  WRONG_ORDER("주문 오류"),
  OTHER("기타 사유");

  private final String description;
}
