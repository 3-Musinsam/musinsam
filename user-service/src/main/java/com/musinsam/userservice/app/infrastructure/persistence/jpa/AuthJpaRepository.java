package com.musinsam.userservice.app.infrastructure.persistence.jpa;

import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.musinsam.userservice.app.domain.user.repository.auth.AuthRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthJpaRepository extends
    JpaRepository<UserEntity, Long>,
    AuthRepository {

}
