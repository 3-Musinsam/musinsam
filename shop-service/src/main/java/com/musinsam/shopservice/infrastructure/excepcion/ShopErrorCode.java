package com.musinsam.shopservice.infrastructure.excepcion;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ShopErrorCode implements ErrorCode {
  SHOP_NOT_FOUND(-1, "존재하지 않는 상점입니다.", HttpStatus.NOT_FOUND);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
