package com.musinsam.alarmservice.infrastructure.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackFeignConfig {

  @Value("${slack.bot-token}")
  private String botToken;

  /**
   * Creates a Feign RequestInterceptor that adds Slack authentication and content type headers to outgoing requests.
   *
   * The interceptor sets the "Authorization" header with the Slack bot token and the "Content-Type" header to "application/json" for all Feign client requests.
   *
   * @return a RequestInterceptor that configures Slack API request headers
   */
  @Bean
  public RequestInterceptor slackRequestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("Authorization", "Bearer " + botToken);
      requestTemplate.header("Content-Type", "application/json");
    };
  }
}
