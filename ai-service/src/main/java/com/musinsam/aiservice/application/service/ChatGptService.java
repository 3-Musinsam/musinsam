package com.musinsam.aiservice.application.service;

import com.musinsam.aiservice.application.dto.request.ChatGptRequest;
import com.musinsam.aiservice.application.dto.response.ChatGptResponse;
import com.musinsam.aiservice.infrastructure.client.AiClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatGptService {

  private final AiClient aiClient;

  @Value("${OPENAI-API-KEY}")
  private String openaiApiKey;

  public String completeChat(String prompt) {
    List<ChatGptRequest.Message> messages = List.of(new ChatGptRequest.Message("user", prompt));

    ChatGptRequest request = new ChatGptRequest(
        "gpt-3.5-turbo",
        messages,
        0.9
    );

    ChatGptResponse response = aiClient.aiCompletions("Bearer " + openaiApiKey, request);

    return response.getChoices().get(0).getMessage().getContent();
  }
}
