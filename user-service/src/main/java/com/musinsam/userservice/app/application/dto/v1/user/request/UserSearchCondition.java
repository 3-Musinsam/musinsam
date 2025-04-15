package com.musinsam.userservice.app.application.dto.v1.user.request;

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
public class UserSearchCondition {
  private Long id;
  private String email;
  private String name;
  private UserRoleType userRoleType;
  private ZonedDateTime createdFrom;
  private ZonedDateTime createdTo;
  private ZonedDateTime updatedFrom;
  private ZonedDateTime updatedTo;
  private ZonedDateTime deletedFrom;
  private ZonedDateTime deletedTo;
  private String createdBy;
  private String updatedBy;
  private String deletedBy;


}