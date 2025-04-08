package com.musinsam.common.user;

public record CurrentUserDtoApiV1(Long userId, UserRoleType role) {
  public static CurrentUserDtoApiV1 of(Long userId, UserRoleType role) {
    return new CurrentUserDtoApiV1(userId, role);
  }
}