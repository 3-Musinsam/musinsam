package com.musinsam.alarmservice.application.dto.response;

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

    private UUID id;
    private String message;

    public static Alarm from(AlarmEntity alarmEntity) {
      return Alarm.builder()
          .id(alarmEntity.getId())
          .message(alarmEntity.getMessage())
          .build();
    }
  }
}
