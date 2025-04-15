package com.musinsam.userservice.app.application.dto.v1.user.response;

import com.musinsam.userservice.app.domain.user.entity.UserEntity;
import com.musinsam.userservice.app.domain.user.vo.UserRoleType;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResUsersGetDtoApiV1 {

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

  public static ResUsersGetDtoApiV1 from(UserEntity userEntity) {
    return ResUsersGetDtoApiV1.builder()
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