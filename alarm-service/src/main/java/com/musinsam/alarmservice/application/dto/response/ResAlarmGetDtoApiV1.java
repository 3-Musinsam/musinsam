package com.musinsam.alarmservice.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsam.alarmservice.application.dto.response.ResAlarmGetDtoApiV1.AlarmPage.Alarm;
import com.musinsam.alarmservice.domain.alarm.entity.AlarmEntity;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResAlarmGetDtoApiV1 {

  private AlarmPage alarmPage;

  public static ResAlarmGetDtoApiV1 of(Page<AlarmEntity> alarmEntityPage) {
    return ResAlarmGetDtoApiV1.builder()
        .alarmPage(new AlarmPage(alarmEntityPage))
        .build();
  }

  @Getter
  @ToString
  public static class AlarmPage extends PagedModel<Alarm> {

    public AlarmPage(Page<AlarmEntity> alarmEntityPage) {
      super(
          new PageImpl<>(
              Alarm.from(alarmEntityPage.getContent()),
              alarmEntityPage.getPageable(),
              alarmEntityPage.getTotalElements()
          )
      );
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

      public static List<Alarm> from(List<AlarmEntity> alarmEntityList) {
        return from(alarmEntityList);
      }

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
}
