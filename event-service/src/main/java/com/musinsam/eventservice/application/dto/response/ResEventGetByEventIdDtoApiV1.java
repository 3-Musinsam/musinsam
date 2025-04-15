package com.musinsam.eventservice.application.dto.response;

import com.musinsam.eventservice.domain.event.EventStatus;
import com.musinsam.eventservice.domain.event.entity.EventEntity;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResEventGetByEventIdDtoApiV1 {

  private Event event;

  public static ResEventGetByEventIdDtoApiV1 of(EventEntity eventEntity) {
    return ResEventGetByEventIdDtoApiV1.builder()
        .event(Event.from(eventEntity))
        .build();
  }


  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Event {

    private UUID id;
    private String name;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private EventStatus status;
    //ㅇㅣ벤트상품


    public static Event from(EventEntity eventEntity) {
      return Event.builder()
          .id(eventEntity.getId())
          .name(eventEntity.getName())
          .startTime(eventEntity.getStartTime())
          .endTime(eventEntity.getEndTime())
          .status(eventEntity.getStatus())
          .build();
    }

  }

}
