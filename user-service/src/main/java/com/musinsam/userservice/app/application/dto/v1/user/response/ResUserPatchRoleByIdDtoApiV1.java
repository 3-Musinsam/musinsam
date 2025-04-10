package com.musinsam.userservice.app.application.dto.v1.user.response;


import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.musinsam.userservice.app.domain.user.vo.UserRoleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ResUserPatchRoleByIdDtoApiV1 {

  private User user;

  public static ResUserPatchRoleByIdDtoApiV1 of(UserEntity userEntity) {
    return ResUserPatchRoleByIdDtoApiV1.builder()
        .user(User.from(userEntity))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class User {
    private Long id;
    private UserRoleType newRole;

    public static User from(UserEntity userEntity) {
      return User.builder()
          .id(userEntity.getId())
          .newRole(userEntity.getUserRoleType())
          .build();
    }
  }
}
