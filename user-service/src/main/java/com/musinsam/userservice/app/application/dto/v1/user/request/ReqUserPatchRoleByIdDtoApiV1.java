package com.musinsam.userservice.app.application.dto.v1.user.request;

import com.musinsam.userservice.app.domain.user.vo.UserRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqUserPatchRoleByIdDtoApiV1 {
  private UserRoleType userRoleType;
}
