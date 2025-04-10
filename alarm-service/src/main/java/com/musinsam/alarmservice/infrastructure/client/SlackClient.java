package com.musinsam.alarmservice.infrastructure.client;

import com.musinsam.alarmservice.application.dto.request.SlackRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "slackClient", url = "${slack.webhook.url}${slack.webhook.key}")
public interface SlackClient {

  @PostMapping
  ResponseEntity<String> sendSlack(@RequestBody SlackRequest message);
}
