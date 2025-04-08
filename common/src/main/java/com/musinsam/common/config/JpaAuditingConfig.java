package com.musinsam.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(
    auditorAwareRef = "auditorAwareImpl",
    dateTimeProviderRef = "auditingDateTimeProvider"
)
public class JpaAuditingConfig {}
