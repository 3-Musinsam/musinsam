package com.musinsam.userservice.app.application.service.auth;

import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthSignupDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthSignupDtoApiV1;

public interface AuthService {
  ResAuthSignupDtoApiV1 signup(ReqAuthSignupDtoApiV1 request);
}
