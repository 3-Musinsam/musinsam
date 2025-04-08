package com.musinsam.common.time;

import jakarta.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class TimeZoneContextHolder {

  private static final String HEADER_NAME = "X-USER-TIMEZONE";

  public ZoneId getZoneId() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    if (attributes != null) {
      HttpServletRequest request = attributes.getRequest();
      String zoneIdStr = request.getHeader(HEADER_NAME);
      if (zoneIdStr != null && ZoneId.getAvailableZoneIds().contains(zoneIdStr)) {
        return ZoneId.of(zoneIdStr);
      }
    }

    return ZoneId.of("UTC");
  }
}