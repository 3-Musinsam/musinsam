package com.musinsam.shopservice.infrastructure.feign;

import com.musinsam.common.config.FeignConfig;
import com.musinsam.shopservice.application.dto.request.ReqAiPostDtoApiV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-service", configuration = FeignConfig.class)
public interface AiFeignClientApiV1 {

  @PostMapping("/internal/v1/ai-messages")
  String postBy(@RequestBody ReqAiPostDtoApiV1 dto);

}
