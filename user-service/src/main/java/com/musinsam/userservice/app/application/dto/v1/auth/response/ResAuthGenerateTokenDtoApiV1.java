package com.musinsam.userservice.app.application.dto.v1.auth.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ResAuthGenerateTokenDtoApiV1 {

  private Token token;

  public static ResAuthGenerateTokenDtoApiV1 of(String accessToken) {
    return ResAuthGenerateTokenDtoApiV1.builder()
        .token(Token.from(accessToken))
        .build();
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
