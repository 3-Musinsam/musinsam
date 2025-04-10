package com.musinsam.userservice.app.application.service.auth;

import com.musinsam.common.exception.CustomException;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthSignupDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.response.ResAuthSignupDtoApiV1;
import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.musinsam.userservice.app.domain.user.repository.auth.AuthRepository;
import com.musinsam.userservice.app.global.response.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final AuthRepository authRepository;
  private final PasswordEncoder passwordEncoder;

  private void existsByEmail(String email) {
    if (authRepository.existsByEmail(email)) {
      throw new CustomException(AuthErrorCode.DUPLICATE_EMAIL);
    }
  }

  @Transactional
  @Override
  public ResAuthSignupDtoApiV1 signup(ReqAuthSignupDtoApiV1 req) {
    existsByEmail(req.getUser().getEmail());

    UserEntity userEntity = UserEntity.of(
        req.getUser().getEmail(),
        passwordEncoder.encode(req.getUser().getPassword()),
        req.getUser().getName()
    );

    UserEntity savedUser = authRepository.save(userEntity);

    return ResAuthSignupDtoApiV1.of(savedUser);
  }
}
