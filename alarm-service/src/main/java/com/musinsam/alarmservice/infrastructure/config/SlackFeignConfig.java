package com.musinsam.alarmservice.infrastructure.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackFeignConfig {

  @Value("${slack.bot-token}")
  private String botToken;

  @Bean
  public RequestInterceptor slackRequestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("Authorization", "Bearer " + botToken);
      requestTemplate.header("Content-Type", "application/json");
    };
  }
}
