package com.musinsam.paymentservice.infrastructure.config;

import java.util.Base64;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "toss.payments")
public class PaymentConfig {

  // 결제 위젯 클라이언트 키 (프론트엔드용)
  private String clientKey;

  private String secretKey;

  private String successRedirectUrl;

  private String failRedirectUrl;

  private String customerKeySalt;

  public String getAuthorizationHeader() {
    return "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
  }

  public String generatedCustomerKey(Long userId) {
    return customerKeySalt + userId.toString();
  }
}
