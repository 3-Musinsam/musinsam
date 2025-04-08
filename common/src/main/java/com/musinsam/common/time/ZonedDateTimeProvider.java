package com.musinsam.common.time;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component("auditingDateTimeProvider")
@RequiredArgsConstructor
public class ZonedDateTimeProvider implements DateTimeProvider {

  private final TimeZoneContextHolder timeZoneContextHolder;

  @Override
  @NonNull
  public Optional<TemporalAccessor> getNow() {
    ZoneId zoneId = timeZoneContextHolder.getZoneId();
    return Optional.of(ZonedDateTime.now(zoneId));
  }
}
