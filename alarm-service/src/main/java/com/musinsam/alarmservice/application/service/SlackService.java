package com.musinsam.alarmservice.application.service;

import com.musinsam.alarmservice.application.dto.response.SlackOpenImResponse;
import com.musinsam.alarmservice.infrastructure.feign.SlackFeignClientApiV2;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackService {

  private final SlackFeignClientApiV2 slackFeignClientApiV2;

  public void sendDmToUser(String userId, String message) {
    // 1. DM 채널 오픈
    Map<String, Object> openRequest = Map.of("users", userId);
    SlackOpenImResponse openResponse = slackFeignClientApiV2.openImChannel(openRequest);

    String channelId = openResponse.getChannel().getId();

    // 2. 메시지 전송
    Map<String, Object> messageRequest = Map.of(
        "channel", channelId,
        "text", message
    );
    slackFeignClientApiV2.postMessage(messageRequest);
  }
}
