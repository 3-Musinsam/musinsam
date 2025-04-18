package com.musinsam.shopservice.infrastructure.excepcion;

import com.musinsam.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ShopResponseCode implements SuccessCode {

  SHOP_CREATE_SUCCESS(0, "상점 생성 성공", HttpStatus.CREATED),
  SHOP_GET_LIST_SUCCESS(0, "상점 목록 조회 성공", HttpStatus.OK),
  SHOP_GET_SUCCESS(0, "상점 상세 조회 성공", HttpStatus.OK),
  SHOP_UPDATE_SUCCESS(0, "상점 수정 성공", HttpStatus.OK),
  SHOP_DELETE_SUCCESS(0, "상점 삭제 성공", HttpStatus.OK),
  SHOP_GET_COUPON_LIST_SUCCESS(0, "상점 쿠폰 목록 조회 성공", HttpStatus.OK);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
