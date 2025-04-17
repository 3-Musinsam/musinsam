package com.musinsam.aiservice.application.service;

import com.musinsam.aiservice.application.dto.request.ChatGptRequest;
import com.musinsam.aiservice.application.dto.request.ReqAiPostDtoApiV1;
import com.musinsam.aiservice.application.dto.response.ChatGptResponse;
import com.musinsam.aiservice.infrastructure.feign.ChatGptFeignClientApiV1;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatGptService {

  private final ChatGptFeignClientApiV1 chatGptFeignClientApiV1;

  @Value("${openai.api.key}")
  private String openaiApiKey;

  public String completeChat(ReqAiPostDtoApiV1 dto) {
    List<ChatGptRequest.Message> messages = List.of(
        new ChatGptRequest.Message("user", dto.getAi().getPrompt()));

    ChatGptRequest request = new ChatGptRequest(
        "gpt-3.5-turbo",
        messages,
        0.9
    );

    ChatGptResponse response = chatGptFeignClientApiV1.aiCompletions("Bearer " + openaiApiKey,
        request);

    return response.getChoices().get(0).getMessage().getContent();
  }
}
