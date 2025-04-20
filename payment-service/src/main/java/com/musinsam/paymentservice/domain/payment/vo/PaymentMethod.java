package com.musinsam.paymentservice.domain.payment.vo;

import com.musinsam.common.exception.CustomException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
  CARD("카드"),
  VIRTUAL_ACCOUNT("가상계좌"),
  EASY_PAYMENT("간편결제"),
  MOBILE_PHONE("휴대폰"),
  ACCOUNT_TRANSFER("계좌이체"),
  CULTURE_GIFT_CERTIFICATE("문화상품권"),
  BOOK_GIFT_CERTIFICATE("도서문화상품권"),
  GAME_GIFT_CERTIFICATE("게임문화상품권"),
  ;

  private final String description;

  public static PaymentMethod descriptionOf(final String description) {
    return Arrays.stream(values())
        .filter(method -> method.getDescription().equals(description))
        .findFirst()
        .orElseThrow(() -> CustomException.from(PaymentErrorCode.PAYMENT_INVALID_PAYMENT_METHOD));
  }
}
