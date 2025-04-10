package com.musinsam.paymentservice.domain.payment.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
  PENDING("결제 대기"),
  INITIALIZED("결제 초기화"),
  READY("결제 준비"),
  IN_PROGRESS("결제 진행 중"),
  DONE("결제 완료"),
  CANCELED("결제 취소"),
  ABORTED("결제 중단"),
  FAILED("결제 실패");

  private final String description;
}
