package com.musinsam.aiservice.presentation.controller;

import com.musinsam.aiservice.application.dto.request.ReqAiPostDtoApiV1;
import com.musinsam.aiservice.application.service.ChatGptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/ai-messages")
public class AiInternalControllerApiV1 {

  private final ChatGptService chatGptService;

  @PostMapping
  public ResponseEntity<String> postBy(@Valid @RequestBody ReqAiPostDtoApiV1 dto) {
    String message = chatGptService.completeChat(dto);

    return ResponseEntity.ok(message);
  }
}
