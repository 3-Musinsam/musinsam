package com.musinsam.userservice.app.domain.user.repository.auth;

import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import java.util.Optional;

public interface AuthRepository {
  Optional<UserEntity> findByEmail(String email);
  Boolean existsByEmail(String email);
  UserEntity save(UserEntity user);
}
