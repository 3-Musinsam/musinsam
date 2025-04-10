package com.musinsam.alarmservice.infrastructure.client;

import com.musinsam.common.config.FeignConfig;
import com.musinsam.common.request.AiPromptRequest;
import com.musinsam.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-service", configuration = FeignConfig.class)
public interface AiClient {

  @PostMapping("/v1/ais")
  ApiResponse<String> getCompletions(@RequestBody AiPromptRequest<?> request);

}
