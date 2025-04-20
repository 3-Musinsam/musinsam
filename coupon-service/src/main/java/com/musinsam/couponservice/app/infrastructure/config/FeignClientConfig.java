package com.musinsam.couponservice.app.infrastructure.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@EnableFeignClients
public class FeignClientConfig {

  private static final String USER_ID_HEADER_NAME = "X-User-Id";
  private static final String USER_ROLE_HEADER_NAME = "X-User-Role";

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (null != attributes) {
        HttpServletRequest request = attributes.getRequest();
        String userId = request.getHeader(USER_ID_HEADER_NAME);
        String role = request.getHeader(USER_ROLE_HEADER_NAME);
        if (userId != null) {
          requestTemplate.header(USER_ID_HEADER_NAME, userId);
        }
        if (role != null) {
          requestTemplate.header(USER_ROLE_HEADER_NAME, role);
        }
      }
    };
  }
}