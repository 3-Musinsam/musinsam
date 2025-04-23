package com.musinsam.alarmservice.infrastructure.feign;

import com.musinsam.alarmservice.application.dto.request.SlackRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "slackClient1", url = "${slack.webhook.url}${slack.webhook.key}")
public interface SlackFeignClientApiV1 {

  /**
   * Sends a message to a Slack webhook endpoint.
   *
   * @param message the Slack message payload to send
   * @return the HTTP response from the Slack webhook
   */
  @PostMapping
  ResponseEntity<String> sendSlack(@RequestBody SlackRequest message);
}
