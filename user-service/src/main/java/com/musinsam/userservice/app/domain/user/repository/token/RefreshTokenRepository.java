package com.musinsam.userservice.app.domain.user.repository.token;

import java.util.Optional;

public interface RefreshTokenRepository {

  void saveRefreshToken(Long userId, String refreshToken, long ttlMillis);

  Optional<String> getRefreshToken(Long userId);

  void deleteRefreshToken(Long userId);
}
