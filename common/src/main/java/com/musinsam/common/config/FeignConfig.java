package com.musinsam.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig implements RequestInterceptor {

  private final String X_USER_ROLE = "X-User-Role";
  private final String X_USER_ID = "X-User-Id";

  @Override
  public void apply(RequestTemplate template) {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    if (attributes != null) {
      HttpServletRequest request = attributes.getRequest();
      String userRole = request.getHeader(X_USER_ROLE);
      String userId = request.getHeader(X_USER_ID);

      if (userId != null) {
        template.header(X_USER_ID, userId);
      }
      if (userRole != null) {
        template.header(X_USER_ROLE, userRole);
      }
    }
  }
}
