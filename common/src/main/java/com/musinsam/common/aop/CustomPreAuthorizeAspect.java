package com.musinsam.common.aop;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.UserRoleType;
import com.musinsam.common.exception.CommonErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j(topic = "CustomPreAuthorizeAspect")
public class CustomPreAuthorizeAspect {

  @Before("@annotation(customPreAuthorize)")
  public void customPreAuthorized(JoinPoint joinPoint, CustomPreAuthorize customPreAuthorize) throws Throwable {
    Set<UserRoleType> allowedRoles = Set.of(customPreAuthorize.userRoleType());
    UserRoleType currentUserRole = getCurrentUserRoleType();

    log.debug("User role: {}, Required roles: {}", currentUserRole, allowedRoles);

    if (!allowedRoles.contains(currentUserRole)) {
      log.warn("Access denied! Current role: {}, Required roles: {}", currentUserRole, allowedRoles);
      throw CustomException.from(CommonErrorCode.FORBIDDEN);
    }
  }

  private UserRoleType getCurrentUserRoleType() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    if (attributes == null) {
      log.warn("No request context available.");
      throw CustomException.from(CommonErrorCode.UNAUTHORIZED);
    }

    HttpServletRequest request = attributes.getRequest();
    String userRoleStr = request.getHeader("X-USER-ROLE");

    if (userRoleStr == null || userRoleStr.isBlank()) {
      log.warn("Missing or empty 'X-USER-ROLE' header.");
      throw CustomException.from(CommonErrorCode.UNAUTHORIZED);
    }

    try {
      return UserRoleType.valueOf(userRoleStr);
    } catch (IllegalArgumentException e) {
      log.warn("Invalid role value in header: '{}'", userRoleStr);
      throw CustomException.from(CommonErrorCode.UNAUTHORIZED);
    }
  }
}