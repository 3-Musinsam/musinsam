package com.musinsam.alarmservice.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.alarmservice.application.dto.request.ReqAlarmPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AlarmKafkaConsumer {

  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "coupon.alarm", groupId = "alarm-service-group")
  public void listen(String message) {
    try {
      ReqAlarmPostDtoApiV1 alarmDto = objectMapper.readValue(message, ReqAlarmPostDtoApiV1.class);
      // 실제 알림 처리 로직
      log.info("알림 수신: {}", alarmDto.getAlarm().getMessage());
    } catch (Exception e) {
      log.error("Kafka 메시지 처리 실패", e);
    }
  }
}
