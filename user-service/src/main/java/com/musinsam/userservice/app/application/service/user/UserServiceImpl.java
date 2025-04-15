package com.musinsam.userservice.app.application.service.user;

import static com.musinsam.userservice.app.domain.user.vo.UserErrorCode.USER_GET_FORBIDDEN;
import static com.musinsam.userservice.app.domain.user.vo.UserErrorCode.USER_NOT_FOUND;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.request.ReqUserPatchRoleByIdDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.request.UserSearchCondition;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserDeleteByIdDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserGetByIdDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserPatchRoleByIdDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUsersGetDtoApiV1;
import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.musinsam.userservice.app.domain.user.repository.user.UserQueryRepository;
import com.musinsam.userservice.app.domain.user.repository.user.UserRepository;
import com.musinsam.userservice.app.domain.user.vo.UserRoleType;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserQueryRepository userQueryRepository;


  private UserEntity findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
  }

  private void validateAccessRight(Long id, CurrentUserDtoApiV1 currentUser) {
    if (!currentUser.role().toString().equals(UserRoleType.ROLE_MASTER.toString()) && !currentUser.userId()
        .equals(id)) {
      throw new CustomException(USER_GET_FORBIDDEN);
    }
  }

  @Transactional
  @Override
  public ResUserGetByIdDtoApiV1 getUser(Long id, CurrentUserDtoApiV1 currentUser) {
    validateAccessRight(id, currentUser);
    UserEntity userEntity = findUserById(id);

    return ResUserGetByIdDtoApiV1.of(userEntity);
  }

  @Transactional
  @Override
  public ResUserDeleteByIdDtoApiV1 deleteUser(Long id, CurrentUserDtoApiV1 currentUser) {
    validateAccessRight(id, currentUser);
    UserEntity userEntity = findUserById(id);
    userEntity.softDelete(currentUser.userId(), ZoneId.systemDefault());
    UserEntity savedUserEntity = userRepository.save(userEntity);

    return ResUserDeleteByIdDtoApiV1.of(savedUserEntity);
  }


  @Transactional(readOnly = true)
  @Override
  public Page<ResUsersGetDtoApiV1> getUsersByCondition(UserSearchCondition condition, Pageable pageable) {
    return userQueryRepository.findUsersByCondition(condition, pageable);
  }

  @Transactional
  @Override
  public ResUserPatchRoleByIdDtoApiV1 patchUserRoleById(
      Long id,
      CurrentUserDtoApiV1 currentUser,
      ReqUserPatchRoleByIdDtoApiV1 request) {
    validateAccessRight(id, currentUser);
    UserEntity userEntity = findUserById(id);

    userEntity.updateRole(request.getUserRoleType());
    userRepository.save(userEntity);
    return ResUserPatchRoleByIdDtoApiV1.of(userEntity);
  }
}
