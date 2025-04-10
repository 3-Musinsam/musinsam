package com.musinsam.userservice.app.presentation.v1;

import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;
import static com.musinsam.userservice.app.global.response.UserResponseCode.USER_DELETE_BY_ID_SUCCESS;
import static com.musinsam.userservice.app.global.response.UserResponseCode.USERS_GET_SUCCESS;
import static com.musinsam.userservice.app.global.response.UserResponseCode.USER_GET_SUCCESS;
import static com.musinsam.userservice.app.global.response.UserResponseCode.USER_PATCH_ROLE_BY_ID_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.common.user.UserRoleType;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserGetByIdDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserGetDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserPatchRoleByIdDtoApiV1;
import java.time.ZonedDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/users")
@RestController
public class UserControllerApiV1 {

  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ResUserGetByIdDtoApiV1>> getUser(
      @PathVariable Long id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        USER_GET_SUCCESS.getCode(),
        USER_GET_SUCCESS.getMessage(),
        null
    ));
  }


  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_COMPANY, ROLE_MASTER})
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> deleteUser(
      @PathVariable Long id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        USER_DELETE_BY_ID_SUCCESS.getCode(),
        USER_DELETE_BY_ID_SUCCESS.getMessage(),
        null
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_MASTER})
  @GetMapping
  public ResponseEntity<ApiResponse<PagedModel<ResUserGetDtoApiV1>>> getUsers(
      @RequestParam(required = false) Long id,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) UserRoleType userRoleType,
      @RequestParam(required = false) Boolean isDeleted,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime createdFrom,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime createdTo,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime updatedFrom,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime updatedTo,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime deletedFrom,
      @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) ZonedDateTime deletedTo,
      @RequestParam(required = false) String createdBy,
      @RequestParam(required = false) String updatedBy,
      @RequestParam(required = false) String deletedBy,
      @PageableDefault(size = 10)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
          @SortDefault(sort = "id", direction = Sort.Direction.DESC)
      }) Pageable pageable
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        USERS_GET_SUCCESS.getCode(),
        USERS_GET_SUCCESS.getMessage(),
        null
    ));
  }

  @CustomPreAuthorize(userRoleType = {ROLE_MASTER})
  @PatchMapping("/{id}/role")
  public ResponseEntity<ApiResponse<ResUserPatchRoleByIdDtoApiV1>> patchUserRoleById(
      @PathVariable Long id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return ResponseEntity.ok(new ApiResponse<>(
        USER_PATCH_ROLE_BY_ID_SUCCESS.getCode(),
        USER_PATCH_ROLE_BY_ID_SUCCESS.getMessage(),
        null
    ));
  }
}
