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
public class ResEventGetDtoApiV1 {

  private Event event;

  public static ResEventGetDtoApiV1 of(EventEntity eventEntity) {
    return ResEventGetDtoApiV1.builder()
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


    public static Event from(EventEntity eventEntity) {
      return Event.builder()
          .id(null)
          .name(null)
          .startTime(null)
          .endTime(null)
          .status(null)
          .build();
    }

  }

}
