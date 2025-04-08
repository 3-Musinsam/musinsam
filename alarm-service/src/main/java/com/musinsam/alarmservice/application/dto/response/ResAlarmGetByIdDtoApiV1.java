package com.musinsam.alarmservice.application.dto.response;

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
public class ResAlarmGetByIdDtoApiV1 {

  private Alarm alarm;

  // TODO: UserEntity 추가시 of 추가 필요
  public static ResAlarmGetByIdDtoApiV1 of(AlarmEntity alarmEntity) {
    return ResAlarmGetByIdDtoApiV1.builder()
        .alarm(Alarm.from(alarmEntity))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Alarm {

    @JsonProperty
    private UUID id;
    @JsonProperty("order-id")
    private UUID orderId;
    @JsonProperty("coupon-id")
    private UUID couponId;
    @JsonProperty
    private String message;

    // TODO: UserEntity 추가시 from 추가 필요
    public static Alarm from(AlarmEntity alarmEntity) {
      return Alarm.builder()
          .id(alarmEntity.getId())
          .orderId(alarmEntity.getOrderId())
          .couponId(alarmEntity.getCouponId())
          .message(alarmEntity.getMessage())
          .build();
    }
  }
}
