package com.musinsam.alarmservice.infrastructure.feign;

import com.musinsam.alarmservice.application.dto.response.SlackOpenImResponse;
import com.musinsam.alarmservice.infrastructure.config.SlackFeignConfig;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "slackClient2", url = "https://slack.com/api", configuration = SlackFeignConfig.class)
public interface SlackFeignClientApiV2 {

  /**
   * Opens a direct message channel in Slack using the provided request payload.
   *
   * @param request the payload containing parameters required by the Slack API to open a conversation
   * @return the response from Slack containing details about the opened IM channel
   */
  @PostMapping("/conversations.open")
  SlackOpenImResponse openImChannel(@RequestBody Map<String, Object> request);

  /**
   * Sends a message to a Slack channel using the chat.postMessage API endpoint.
   *
   * @param request a map containing the message payload and parameters required by Slack's API
   */
  @PostMapping("/chat.postMessage")
  void postMessage(@RequestBody Map<String, Object> request);
}
