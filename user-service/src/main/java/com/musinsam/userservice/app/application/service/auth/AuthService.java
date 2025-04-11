package com.musinsam.userservice.app.application.service.auth;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthGenerateTokenDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthLogoutDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthSignupDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthGenerateTokenDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthSignupDtoApiV1;

public interface AuthService {
  ResAuthSignupDtoApiV1 signup(ReqAuthSignupDtoApiV1 request);

  ResAuthLoginDtoApiV1 login(ReqAuthLoginDtoApiV1 request);

  void logout(String bearerToken, CurrentUserDtoApiV1 request);

  ResAuthGenerateTokenDtoApiV1 generateToken(ReqAuthGenerateTokenDtoApiV1 request);
}
