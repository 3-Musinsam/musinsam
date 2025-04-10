package com.musinsam.userservice.app.domain.auth.jwt;

import com.musinsam.userservice.app.domain.user.vo.UserRoleType;

public interface JwtProvider {
  String createAccessToken(Long id, UserRoleType role);

  String createRefreshToken(Long id, UserRoleType role);

  Boolean validateToken(String token);

  Long getUserIdFromToken(String token);

  UserRoleType getUserRoleFromToken(String token);

  Long getRefreshTokenExpiration();

}
