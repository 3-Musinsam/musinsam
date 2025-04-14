package com.musinsam.userservice.app.application.dto.v1.auth.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResAuthLoginWithCookieDtoApiV1 {
  private ResAuthLoginDtoApiV1 resAuthLoginDtoApiV1;
  private ResponseCookie refreshCookie;

  public static ResAuthLoginWithCookieDtoApiV1 of(ResAuthLoginDtoApiV1 resAuthLoginDtoApiV1, ResponseCookie refreshCookie) {
    return new ResAuthLoginWithCookieDtoApiV1(resAuthLoginDtoApiV1, refreshCookie);
  }
}
