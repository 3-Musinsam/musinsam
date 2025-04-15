package com.musinsam.userservice.app.infrastructure.persistence.jpa;

import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.musinsam.userservice.app.domain.user.repository.user.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends
    JpaRepository<UserEntity, Long>,
    UserRepository {
}
