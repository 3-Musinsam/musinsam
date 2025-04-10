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

  public static ResAuthGenerateTokenDtoApiV1 of(Token token) {
    return ResAuthGenerateTokenDtoApiV1.builder()
        .token(Token.from(token))
        .build();
  }


  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Token {

    private String accessToken;
    private String refreshToken;

    public static Token from(Token token) {
      return Token.builder()
          .accessToken(token.getAccessToken())
          .refreshToken(token.getRefreshToken())
          .build();
    }
  }
}
