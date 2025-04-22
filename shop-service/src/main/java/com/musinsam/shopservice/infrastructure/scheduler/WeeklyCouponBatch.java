package com.musinsam.shopservice.infrastructure.scheduler;

import com.musinsam.shopservice.application.dto.request.ReqAiPostDtoApiV1;
import com.musinsam.shopservice.application.dto.request.ReqAlarmPostDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopCouponDtoApiV1;
import com.musinsam.shopservice.application.service.ShopInternalServiceApiV1;
import com.musinsam.shopservice.infrastructure.feign.AiFeignClientApiV1;
import com.musinsam.shopservice.infrastructure.kafka.AlarmKafkaProducer;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public abstract class WeeklyCouponBatch {

  private final ShopInternalServiceApiV1 shopInternalServiceApiV1;
  private final AiFeignClientApiV1 aiFeignClientApiV1;
  private final AlarmKafkaProducer alarmKafkaProducer;

  // 매주 월요일 오전 3시에 실행
  @Scheduled(cron = "0 0 3 ? * MON")
//  @Scheduled(cron = "0 0/1 * * * *") // 테스트용
  public void runWeeklyCouponCheck() {
    List<UUID> shopIds = getShopIds();
    int totalShopCount = shopIds.size(); // 전체 상점 수
    int totalCouponCount = 0; // 전체 쿠폰 수
    int processedShopCount = 0; // 실제 처리된 상점 수
    long totalStart = System.currentTimeMillis();
    int count = 0;

    for (UUID shopId : shopIds) {
      try {
        long start = System.currentTimeMillis();
        log.info("쿠폰 조회 시작 - shopId: {}", shopId);
        // 쿠폰 조회
        ResShopCouponDtoApiV1 res = shopInternalServiceApiV1.internalCouponGetByShopId(shopId);

        // 조회된 쿠폰이 null이 아니고, couponList가 비어있지 않다면 진행
        if (res != null && res.getCouponList() != null && !res.getCouponList().isEmpty()) {
          for (ResShopCouponDtoApiV1.Coupon coupon : res.getCouponList()) {
            try {
              ReqAiPostDtoApiV1 aiRequest = buildAiPrompt(coupon, shopId);
              String slackMessage = aiFeignClientApiV1.postBy(aiRequest);

              log.info("AI 응답 메시지: {}", slackMessage);

              ReqAlarmPostDtoApiV1 slackDto = ReqAlarmPostDtoApiV1.builder()
                  .alarm(ReqAlarmPostDtoApiV1.Alarm.builder()
                      .message(slackMessage)
                      .build())
                  .build();

              alarmKafkaProducer.sendAlarm(slackDto);

              log.info("Slack 알림 전송 완료: shopId={}, couponId={}, message={}",
                  shopId, coupon.getCouponId(), slackMessage);

            } catch (Exception innerEx) {
              log.error("개별 쿠폰 메시지 전송 실패: shopId={}, couponId={}", shopId, coupon.getCouponId(),
                  innerEx);
            }
            totalCouponCount++;
          }
          long end = System.currentTimeMillis();
          log.info("shopId={}, took {}ms", shopId, end - start);
          processedShopCount++; // 쿠폰 있는 상점
        } else {
          // 쿠폰이 없거나 비어있는 경우에는 추가 로그를 출력하고 넘어가도록 처리
          log.info("쿠폰이 없거나 비어있음: shopId={}", shopId);
        }

      } catch (Exception e) {
        log.error("쿠폰 알림 배치 실패: shopId={}", shopId, e);
      }
    }
    long totalEnd = System.currentTimeMillis();
    long totalTime = totalEnd - totalStart;
    log.info("전체 소요 시간: {}ms, 상점 수: {}, 처리된 상점 수: {}, 총 쿠폰 수: {}, 평균 처리 시간: {}ms",
        totalTime, totalShopCount, processedShopCount, totalCouponCount,
        processedShopCount == 0 ? 0 : totalTime / processedShopCount);
  }

  private ReqAiPostDtoApiV1 buildAiPrompt(ResShopCouponDtoApiV1.Coupon coupon, UUID shopId) {
    var policy = coupon.getCouponPolicy();

    if (policy == null) {
      throw new IllegalArgumentException("쿠폰 정책 정보가 없습니다.");
    }

    String prompt = String.format("""
            상점 ID: %s
            쿠폰 ID: %s
            쿠폰명: %s
            유효기간: %s ~ %s 까지
            
            이 쿠폰 정보를 바탕으로 고객에게 Slack 알림 메시지를 보낼 수 있도록 자연스러운 마케팅 문구를 생성해 주세요.
            """,
        shopId,
        coupon.getCouponId(),
        coupon.getCouponName(),
        policy.getStartTime(),
        policy.getEndTime()
    );

    log.info("AI 프롬프트 생성 완료: shopId={}, couponId={}", shopId, coupon.getCouponId());

    return ReqAiPostDtoApiV1.builder()
        .ai(ReqAiPostDtoApiV1.Ai.builder()
            .prompt(prompt)
            .build())
        .build();
  }

  // 구현체에서 정의
  protected abstract List<UUID> getShopIds();
}
