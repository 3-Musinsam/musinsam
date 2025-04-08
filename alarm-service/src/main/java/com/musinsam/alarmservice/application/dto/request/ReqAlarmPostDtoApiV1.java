package com.musinsam.alarmservice.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsam.alarmservice.domain.alarm.entity.AlarmEntity;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqAlarmPostDtoApiV1 {

  private Alarm alarm;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Alarm {

    @JsonProperty("order-id")
    private UUID orderId;

    @JsonProperty("coupon-id")
    private UUID couponId;

    @JsonProperty
    private String message;

    public AlarmEntity toEntityWith(ReqAlarmPostDtoApiV1 reqAlarmPostDtoApiV1) {
      return AlarmEntity.builder()
          .orderId(reqAlarmPostDtoApiV1.getAlarm().orderId)
          .couponId(reqAlarmPostDtoApiV1.getAlarm().couponId)
          .message(reqAlarmPostDtoApiV1.getAlarm().message)
          .build();
    }
  }
}
