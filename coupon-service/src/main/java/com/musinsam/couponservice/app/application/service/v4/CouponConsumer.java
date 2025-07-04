package com.musinsam.couponservice.app.application.service.v4;

import com.musinsam.couponservice.app.application.dto.v4.coupon.response.IssueMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponConsumer {

  private final CouponService couponService;

  @KafkaListener(topics = "coupon-issue-requests", groupId = "coupon-service", containerFactory = "couponKafkaListenerContainerFactory")
  public void consumeCouponIssueRequest(IssueMessage message) {
    try {
      log.info("Received coupon issue request: {}", message);
      couponService.issueCoupon(message);
    } catch (Exception e) {
      log.error("Failed to process coupon issue request: {}", e.getMessage(), e);
    }
  }
}
