package com.musinsam.userservice.app.domain.user.repository.user;

import com.musinsam.userservice.app.application.dto.v1.user.request.UserSearchCondition;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUsersGetDtoApiV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {
  Page<ResUsersGetDtoApiV1> findUsersByCondition(UserSearchCondition condition, Pageable pageable);
}
