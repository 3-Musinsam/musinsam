package com.musinsam.aiservice.infrastructure.client;

import com.musinsam.aiservice.application.dto.request.ChatGptRequest;
import com.musinsam.aiservice.application.dto.response.ChatGptResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "OpenAiClient", url = "https://api.openai.com/v1/", configuration = ChatGptRequest.class)
public interface AiClient {

  @PostMapping("/chat/completions")
  ChatGptResponse aiCompletions(@RequestHeader("Authorization") String apiKey,
      ChatGptRequest request);

}
