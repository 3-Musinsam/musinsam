package com.musinsam.userservice.app.application.service.auth;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthSignupDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthGenerateWithCookieDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthLoginWithCookieDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthSignupDtoApiV1;
import org.springframework.http.ResponseCookie;

public interface AuthService {
  ResAuthSignupDtoApiV1 signup(ReqAuthSignupDtoApiV1 request);

  ResAuthLoginWithCookieDtoApiV1 login(ReqAuthLoginDtoApiV1 request);

  ResponseCookie logout(String bearerToken, CurrentUserDtoApiV1 request);

  ResAuthGenerateWithCookieDtoApiV1 generateToken(String refreshToken, String bearerToken);
}
