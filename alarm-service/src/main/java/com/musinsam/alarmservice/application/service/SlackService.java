package com.musinsam.alarmservice.application.service;

import com.musinsam.alarmservice.application.dto.response.SlackOpenImResponse;
import com.musinsam.alarmservice.infrastructure.feign.SlackFeignClientApiV2;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlackService {

  private final SlackFeignClientApiV2 slackFeignClientApiV2;

  public void sendDmToUser(String userId, String message) {
    log.info("Slack DM 전송 시작 - 대상 사용자: {}", userId);
    // 1. DM 채널 오픈
    Map<String, Object> openRequest = Map.of("users", userId);
    log.debug("Slack 채널 오픈 요청: {}", openRequest);
    SlackOpenImResponse openResponse = slackFeignClientApiV2.openImChannel(openRequest);
    log.debug("Slack 채널 오픈 응답: {}", openResponse);

    String channelId = openResponse.getChannel().getId();
    log.info("Slack 채널 오픈 성공 - 채널 ID: {}", channelId);

    // 2. 메시지 전송
    Map<String, Object> messageRequest = Map.of(
        "channel", channelId,
        "text", message
    );
    log.debug("Slack 메시지 전송 요청: {}", messageRequest);
    slackFeignClientApiV2.postMessage(messageRequest);
    log.info("Slack DM 전송 완료 - 채널: {}", channelId);
  }
}
