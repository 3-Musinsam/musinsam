package com.musinsam.aiservice.application.dto.request;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ChatGptRequest implements Serializable {

  public String model;
  public List<Message> messages;
  public double temperature;

  @AllArgsConstructor
  @NoArgsConstructor
  public static class Message {

    public String role;
    public String content;

  }
}
