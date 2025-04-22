package com.musinsam.shopservice.presentation.controller;

import com.musinsam.shopservice.infrastructure.scheduler.WeeklyCouponBatchImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/shops/batch")
public class WeeklyCouponBatchControllerV1 {

  private final WeeklyCouponBatchImpl weeklyCouponBatchImpl;

  @PostMapping("/weekly-coupon")
  public ResponseEntity<String> runWeeklyCouponBatch() {
    weeklyCouponBatchImpl.runWeeklyCouponCheck(); // 직접 실행
    return ResponseEntity.ok("Batch triggered successfully");
  }
}
