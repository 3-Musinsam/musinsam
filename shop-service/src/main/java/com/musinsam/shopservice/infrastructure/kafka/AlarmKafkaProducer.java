package com.musinsam.shopservice.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.shopservice.application.dto.request.ReqAlarmPostDtoApiV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AlarmKafkaProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  private static final String TOPIC = "coupon.alarm";

  public void sendAlarm(ReqAlarmPostDtoApiV1 alarmDto) {
    try {
      String message = objectMapper.writeValueAsString(alarmDto);
      kafkaTemplate.send(TOPIC, message);
      log.info("Kafka 알림 전송 성공: {}", message);
    } catch (JsonProcessingException e) {
      log.error("Kafka 알림 직렬화 실패", e);
    }
  }
}
