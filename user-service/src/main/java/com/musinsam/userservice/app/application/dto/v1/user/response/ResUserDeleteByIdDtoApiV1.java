package com.musinsam.userservice.app.application.dto.v1.user.response;

import com.musinsam.userservice.app.application.dto.v1.user.response.ResUserDeleteByIdDtoApiV1.User;
import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.musinsam.userservice.app.domain.user.vo.UserRoleType;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResUserDeleteByIdDtoApiV1 {

  private User user;

  public static ResUserDeleteByIdDtoApiV1 of(UserEntity userEntity) {
    return ResUserDeleteByIdDtoApiV1.builder()
        .user(User.from(userEntity))
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
