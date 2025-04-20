package com.musinsam.orderservice.domain.order.vo;


import static org.springframework.http.HttpStatus.OK;

import com.musinsam.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderResponseCode implements SuccessCode {
  ORDER_POST_SUCCESS(0, "Order created successfully.", OK),
  ORDER_GET_SUCCESS(0, "Order retrieved successfully.", OK),
  ORDER_LIST_GET_SUCCESS(0, "Order list retrieved successfully.", OK),
  ORDER_PUT_SUCCESS(0, "Order updated successfully.", OK),
  ORDER_CANCEL_SUCCESS(0, "Order canceled successfully.", OK),
  ORDER_DELETE_SUCCESS(0, "Order deleted successfully.", OK);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
