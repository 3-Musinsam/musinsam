package com.musinsam.userservice.app.application.dto.v1.auth.response;

import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class ResAuthSignupDtoApiV1 {

  private User user;

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class User {
    private Long id;
    private String email;
    private String name;
    private ZonedDateTime createdAt;
  }

}
