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
public class ResAlarmPostDtoApiV1 {

  private Alarm alarm;

  public static ResAlarmPostDtoApiV1 of(AlarmEntity alarmEntity) {
    return ResAlarmPostDtoApiV1.builder()
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

    public static Alarm from(AlarmEntity alarmEntity) {
      return Alarm.builder()
          .id(alarmEntity.getId())
          .build();
    }
  }
}
