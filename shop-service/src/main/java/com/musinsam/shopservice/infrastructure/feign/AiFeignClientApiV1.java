package com.musinsam.shopservice.infrastructure.feign;

import com.musinsam.common.config.FeignConfig;
import com.musinsam.common.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-service", configuration = FeignConfig.class)
public interface AiFeignClientApiV1 {

  @PostMapping("/internal/v1/ai-messages")
  ApiResponse<String> getCompletions(@RequestBody String request);

}
