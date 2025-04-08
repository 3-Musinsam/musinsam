package com.musinsam.common.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleType {
  ROLE_MASTER("마스터 관리자"),
  ROLE_COMPANY("업체"),
  ROLE_USER("회원");

  private final String description;
}
