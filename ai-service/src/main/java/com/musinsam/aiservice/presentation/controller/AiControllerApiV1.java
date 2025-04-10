package com.musinsam.aiservice.presentation.controller;

import com.musinsam.aiservice.application.service.ChatGptService;
import com.musinsam.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ais")
public class AiControllerApiV1 {

  private final ChatGptService chatGptService;

  @PostMapping
  public ResponseEntity<ApiResponse<String>> postBy(@RequestBody String prompt) {
    String message = chatGptService.completeChat(prompt);

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
