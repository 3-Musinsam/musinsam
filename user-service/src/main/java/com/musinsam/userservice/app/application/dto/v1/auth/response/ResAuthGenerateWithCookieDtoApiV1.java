package com.musinsam.userservice.app.application.dto.v1.auth.response;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResAuthGenerateWithCookieDtoApiV1 {
  private ResAuthGenerateTokenDtoApiV1 resAuthGenerateTokenDtoApiV1;
  private ResponseCookie refreshCookie;

  public static ResAuthGenerateWithCookieDtoApiV1 of(
      ResAuthGenerateTokenDtoApiV1 resAuthGenerateTokenDtoApiV1,
      ResponseCookie refreshCookie) {
    return new ResAuthGenerateWithCookieDtoApiV1(resAuthGenerateTokenDtoApiV1, refreshCookie);
  }
}
