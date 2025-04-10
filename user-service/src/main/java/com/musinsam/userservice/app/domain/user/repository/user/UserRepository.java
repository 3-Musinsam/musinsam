package com.musinsam.userservice.app.domain.user.repository.user;

import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import java.util.Optional;

public interface UserRepository {
  Optional<UserEntity> findByEmail(String email);
}
