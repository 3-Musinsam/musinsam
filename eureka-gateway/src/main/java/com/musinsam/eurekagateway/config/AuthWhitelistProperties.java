package com.musinsam.eurekagateway.config;


import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@ConfigurationProperties(prefix = "auth")
@Component
public class AuthWhitelistProperties {
  private final List<String> whitelist = new ArrayList<>();
}
