package com.musinsam.alarmservice.infrastructure.feign;

import com.musinsam.alarmservice.application.dto.response.SlackOpenImResponse;
import com.musinsam.alarmservice.infrastructure.config.SlackFeignConfig;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "slackClient2", url = "https://slack.com/api", configuration = SlackFeignConfig.class)
public interface SlackFeignClientApiV2 {

  @PostMapping("/conversations.open")
  SlackOpenImResponse openImChannel(@RequestBody Map<String, Object> request);

  @PostMapping("/chat.postMessage")
  void postMessage(@RequestBody Map<String, Object> request);
}
