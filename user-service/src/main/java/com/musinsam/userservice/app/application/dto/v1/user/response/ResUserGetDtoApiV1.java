package com.musinsam.userservice.app.application.dto.v1.user.response;

import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.musinsam.userservice.app.domain.user.vo.UserRoleType;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class ResUserGetDtoApiV1 {

  private List<User> user;

  public static ResUserGetDtoApiV1 of(List<UserEntity> userEntityList) {
    return ResUserGetDtoApiV1.builder()
        .user(userEntityList.stream()
            .map(User::from)
            .toList())
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public static class User {
    private Long id;
    private String email;
    private String name;
    private UserRoleType role;
    private ZonedDateTime createdAt;
    private Long createdBy;
    private ZonedDateTime updatedAt;
    private Long updatedBy;
    private ZonedDateTime deletedAt;
    private Long deletedBy;


    public static User from(UserEntity userEntity) {
      return User.builder()
          .id(userEntity.getId())
          .email(userEntity.getEmail())
          .name(userEntity.getName())
          .role(userEntity.getUserRoleType())
          .createdAt(userEntity.getCreatedAt())
          .createdBy(userEntity.getCreatedBy())
          .updatedAt(userEntity.getUpdatedAt())
          .updatedBy(userEntity.getUpdatedBy())
          .deletedAt(userEntity.getDeletedAt())
          .deletedBy(userEntity.getDeletedBy())
          .build();
    }
  }
}
