package com.musinsam.userservice.app.application.dto.v1.auth.response;

import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.musinsam.userservice.app.domain.user.vo.UserRoleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class ResAuthLoginDtoApiV1 {

  private User user;
  private Token token;

  public static ResAuthLoginDtoApiV1 of(UserEntity userEntity, String accessToken) {
    return ResAuthLoginDtoApiV1.builder()
        .user(User.from(userEntity))
        .token(Token.from(accessToken))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class User {
    private Long userId;
    private String email;
    private String name;
    private UserRoleType userRoleType;

    public static User from(UserEntity userEntity) {
      return User.builder()
          .userId(userEntity.getId())
          .email(userEntity.getEmail())
          .name(userEntity.getName())
          .userRoleType(userEntity.getUserRoleType())
          .build();
    }

  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Token {

    private String accessToken;

    public static Token from(String accessToken) {
      return Token.builder()
          .accessToken(accessToken)
          .build();
    }

  }
}
