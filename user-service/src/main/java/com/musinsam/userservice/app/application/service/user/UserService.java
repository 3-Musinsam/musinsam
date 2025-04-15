package com.musinsam.userservice.app.application.service.user;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.request.UserSearchCondition;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserDeleteByIdDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserGetByIdDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUsersGetDtoApiV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  ResUserGetByIdDtoApiV1 getUser(Long id, CurrentUserDtoApiV1 currentUser);

  ResUserDeleteByIdDtoApiV1 deleteUser(Long id, CurrentUserDtoApiV1 currentUser);

  Page<ResUsersGetDtoApiV1> getUsersByCondition(UserSearchCondition condition, Pageable pageable);


}
