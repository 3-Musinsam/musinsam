package com.musinsam.aiservice.presentation.controller;

import com.musinsam.aiservice.application.dto.request.ReqAiPostDtoApiV1;
import com.musinsam.aiservice.application.service.ChatGptService;
import com.musinsam.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/ai-message")
public class AiControllerApiV1 {

  private final ChatGptService chatGptService;

  @PostMapping
  public ResponseEntity<ApiResponse<String>> postBy(@Valid @RequestBody ReqAiPostDtoApiV1 dto) {
    String message = chatGptService.completeChat(dto);

    return new ResponseEntity<>(
        ApiResponse.<String>builder()
            .code(ApiResponse.success().getCode())
            .message("The ai message was successfully sent.")
            .data(message)
            .build(),
        HttpStatus.OK
    );
  }
}
