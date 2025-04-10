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

  public static ResAuthLoginDtoApiV1 of(UserEntity userEntity, String accessToken, String refreshToken) {
    return ResAuthLoginDtoApiV1.builder()
        .user(User.from(userEntity))
        .token(Token.from(accessToken, refreshToken))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class User {

    private String email;
    private String name;
    private UserRoleType userRoleType;

    public static User from(UserEntity userEntity) {
      return User.builder()
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
    private String refreshToken;

    public static Token from(String accessToken, String refreshToken) {
      return Token.builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .build();
    }

  }
}
