package com.musinsam.alarmservice.application.dto.response;

import lombok.Data;

@Data
public class SlackOpenImResponse {

  private boolean ok;
  private Channel channel;

  @Data
  public static class Channel {

    private String id;
  }
}