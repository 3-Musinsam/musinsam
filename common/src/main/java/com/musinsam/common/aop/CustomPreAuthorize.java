package com.musinsam.common.aop;

import com.musinsam.common.user.UserRoleType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomPreAuthorize {
  UserRoleType[] userRoleType()
      default {
      UserRoleType.ROLE_MASTER,
      UserRoleType.ROLE_COMPANY,
      UserRoleType.ROLE_USER
  };
}