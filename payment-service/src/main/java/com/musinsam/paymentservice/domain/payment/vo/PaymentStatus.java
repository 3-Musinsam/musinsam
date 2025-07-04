package com.musinsam.paymentservice.domain.payment.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
  READY("결제 준비"),
  IN_PROGRESS("결제 진행 중"),
  WAITING_FOR_DEPOSIT("가상 계좌 진행중"),
  DONE("결제 완료"),
  CANCELED("결제 취소"),
  PARTIAL_CANCELED("부분 취소"),
  ABORTED("결제 중단"),
  EXPIRED("결제 만료"),
  FAILED("결제 실패");

  private final String description;
}
