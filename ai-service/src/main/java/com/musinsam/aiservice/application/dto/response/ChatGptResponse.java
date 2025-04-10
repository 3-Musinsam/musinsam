package com.musinsam.aiservice.application.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class ChatGptResponse {

  public String id;
  public String object;
  public long created;
  public String model;
  public ChatGptResponse.Usage usage;
  public List<Choice> choices;

  @Getter
  public static class Usage {

    public int promptTokens;
    public int completionTokens;
    public int totalTokens;
  }

  @Getter
  public static class Choice {

    public ChatGptResponse.Choice.Message message;
    public String finishReason;
    public int index;

    @Getter
    public static class Message {

      public String role;
      public String content;
    }
  }


}