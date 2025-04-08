package com.musinsam.common.resolver;

import com.musinsam.common.exception.CommonErrorCode;
import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.common.user.UserRoleType;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String USER_ID_HEADER = "X-USER-ID";
  private static final String USER_ROLE_HEADER = "X-USER-ROLE";

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(com.musinsam.common.resolver.CurrentUser.class)
        && parameter.getParameterType().equals(CurrentUserDtoApiV1.class);
  }

  @Override
  public Object resolveArgument(
      @NonNull MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest request,
      WebDataBinderFactory binderFactory) {

    String userId = request.getHeader(USER_ID_HEADER);
    String role = request.getHeader(USER_ROLE_HEADER);

    if (userId == null || role == null) {
      throw CustomException.from(CommonErrorCode.UNAUTHORIZED);
    }

    try {
      Long id = Long.parseLong(userId);
      UserRoleType roleType = UserRoleType.valueOf(role);
      return CurrentUserDtoApiV1.of(id, roleType);
    } catch (NumberFormatException e) {
      throw CustomException.from(CommonErrorCode.INVALID_INPUT);
    } catch (IllegalArgumentException e) {
      throw CustomException.from(CommonErrorCode.UNAUTHORIZED);
    }
  }
}