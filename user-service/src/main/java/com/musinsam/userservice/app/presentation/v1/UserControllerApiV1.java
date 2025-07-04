package com.musinsam.userservice.app.presentation.v1;

import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;
import static com.musinsam.userservice.app.domain.user.vo.UserResponseCode.USERS_GET_SUCCESS;
import static com.musinsam.userservice.app.domain.user.vo.UserResponseCode.USER_DELETE_BY_ID_SUCCESS;
import static com.musinsam.userservice.app.domain.user.vo.UserResponseCode.USER_GET_SUCCESS;
import static com.musinsam.userservice.app.domain.user.vo.UserResponseCode.USER_PATCH_ROLE_BY_ID_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.request.ReqUserPatchRoleByIdDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.request.UserSearchCondition;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserDeleteByIdDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserGetByIdDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserPatchRoleByIdDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUsersGetDtoApiV1;
import com.musinsam.userservice.app.application.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UserControllerApiV1 {

  private final UserService userService;

  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ResUserGetByIdDtoApiV1>> getUser(
      @PathVariable Long id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    ResUserGetByIdDtoApiV1 response = userService.getUser(id, currentUser);

    return ResponseEntity.ok(new ApiResponse<>(
        USER_GET_SUCCESS.getCode(),
        USER_GET_SUCCESS.getMessage(),
        response
    ));
  }


  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<ResUserDeleteByIdDtoApiV1>> deleteUser(
      @PathVariable Long id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    ResUserDeleteByIdDtoApiV1 response = userService.deleteUser(id, currentUser);

    return ResponseEntity.ok(new ApiResponse<>(
        USER_DELETE_BY_ID_SUCCESS.getCode(),
        USER_DELETE_BY_ID_SUCCESS.getMessage(),
        response
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_MASTER})
  @GetMapping
  public ResponseEntity<ApiResponse<Page<ResUsersGetDtoApiV1>>> getUsers(
      @ParameterObject UserSearchCondition condition,
      @PageableDefault(size = 10)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          @SortDefault(sort = "id", direction = Sort.Direction.DESC)
      }) Pageable pageable
  ) {

    Page<ResUsersGetDtoApiV1> response = userService.getUsersByCondition(condition, pageable);

    return ResponseEntity.ok(new ApiResponse<>(
        USERS_GET_SUCCESS.getCode(),
        USERS_GET_SUCCESS.getMessage(),
        response
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_MASTER})
  @PatchMapping("/{id}/role")
  public ResponseEntity<ApiResponse<ResUserPatchRoleByIdDtoApiV1>> patchUserRoleById(
      @PathVariable Long id,
      @CurrentUser CurrentUserDtoApiV1 currentUser,
      @RequestBody ReqUserPatchRoleByIdDtoApiV1 request
  ) {

    ResUserPatchRoleByIdDtoApiV1 response = userService.patchUserRoleById(id, currentUser, request);

    return ResponseEntity.ok(new ApiResponse<>(
        USER_PATCH_ROLE_BY_ID_SUCCESS.getCode(),
        USER_PATCH_ROLE_BY_ID_SUCCESS.getMessage(),
        response
    ));
  }
}
