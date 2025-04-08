package com.musinsam.common.domain;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    if (attributes != null) {
      HttpServletRequest request = attributes.getRequest();
      String userId = request.getHeader("X-USER-ID");

      if (userId != null && userId.matches("\\d+")) {
        return Optional.of(Long.parseLong(userId));
      }
    }

    // 시스템 또는 비로그인 사용자의 기본값
    return Optional.of(0L);
  }
}