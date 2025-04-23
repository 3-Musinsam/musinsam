package com.musinsam.shopservice.infrastructure.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.musinsam.shopservice.application.dto.request.ReqAlarmPostDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopCouponDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopCouponDtoApiV1.Coupon;
import com.musinsam.shopservice.application.service.ShopInternalServiceApiV1;
import com.musinsam.shopservice.infrastructure.feign.AiFeignClientApiV1;
import com.musinsam.shopservice.infrastructure.kafka.AlarmKafkaProducer;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WeeklyCouponBatchTest {

  @Mock
  private ShopInternalServiceApiV1 shopInternalServiceApiV1;

  @Mock
  private AiFeignClientApiV1 aiFeignClientApiV1;

  @Mock
  private AlarmKafkaProducer alarmKafkaProducer;

  @Test
  void test_runWeeklyCouponCheck_success() {
    // given
    UUID shopId = UUID.fromString("af80aa19-9c74-49dc-b115-8f4533de0f7b");

    // coupon.getCouponList() 예시 값
    List<Coupon> couponList = List.of(
        Coupon.builder()
            .couponId(UUID.fromString("af80aa19-9c74-49dc-b115-8f4533de0f7c"))
            .couponName("봄맞이 10% 할인 쿠폰")
            .couponPolicy(Coupon.CouponPolicy.builder()
                .startTime(ZonedDateTime.now().minusDays(1))
                .endTime(ZonedDateTime.now())
                .build())
            .build(),
        Coupon.builder()
            .couponId(UUID.fromString("af80aa19-9c74-49dc-b115-8f4533de0f7d"))
            .couponName("선착순 쿠폰 정책")
            .couponPolicy(Coupon.CouponPolicy.builder()
                .startTime(ZonedDateTime.now().minusDays(1))
                .endTime(ZonedDateTime.now())
                .build())
            .build()
    );

    ResShopCouponDtoApiV1 coupon = ResShopCouponDtoApiV1.builder()
        .couponList(couponList)
        .build();

    String aiResponse = "🎉 봄맞이 10% 할인 쿠폰이 도착했습니다! 지금 바로 확인해보세요.";

    // stub
    when(shopInternalServiceApiV1.internalCouponGetByShopId(shopId)).thenReturn(coupon);
    when(aiFeignClientApiV1.postBy(any())).thenReturn(aiResponse);

    // 테스트용으로 getShopIds 하드코딩을 오버라이드
    WeeklyCouponBatch batch = new WeeklyCouponBatch(shopInternalServiceApiV1, aiFeignClientApiV1,
        alarmKafkaProducer) {
      @Override
      protected List<UUID> getShopIds() {
        return List.of(shopId);
      }
    };

    // when
    batch.runWeeklyCouponCheck();

    // then
    ArgumentCaptor<ReqAlarmPostDtoApiV1> captor = ArgumentCaptor.forClass(
        ReqAlarmPostDtoApiV1.class);
    verify(alarmKafkaProducer, times(2)).sendAlarm(captor.capture());

    List<ReqAlarmPostDtoApiV1> capturedAlarms = captor.getAllValues();
    assertEquals("🎉 봄맞이 10% 할인 쿠폰이 도착했습니다! 지금 바로 확인해보세요.",
        capturedAlarms.get(0).getAlarm().getMessage());
    assertEquals("🎉 봄맞이 10% 할인 쿠폰이 도착했습니다! 지금 바로 확인해보세요.",
        capturedAlarms.get(1).getAlarm().getMessage());
  }
}