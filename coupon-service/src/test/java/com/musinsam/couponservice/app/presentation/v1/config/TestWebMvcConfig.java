//package com.musinsam.couponservice.app.presentation.v1.config;
//
//import static com.musinsam.common.user.UserRoleType.ROLE_COMPANY;
//
//import com.musinsam.common.user.CurrentUserDtoApiV1;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.MethodParameter;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@TestConfiguration
//public class TestWebMvcConfig implements WebMvcConfigurer {
//
//  @Bean
//  public HandlerMethodArgumentResolver testCurrentUserArgumentResolver() {
//
//    return new HandlerMethodArgumentResolver() {
//
//      @Override
//      public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.getParameterType().equals(CurrentUserDtoApiV1.class);
//      }
//
//      @Override
//      public Object resolveArgument(MethodParameter parameter,
//                                    ModelAndViewContainer mavContainer,
//                                    NativeWebRequest webRequest,
//                                    WebDataBinderFactory binderFactory) {
//        return new CurrentUserDtoApiV1(1L, ROLE_COMPANY);
//      }
//    };
//  }
//
//  @Override
//  public void addArgumentResolvers(java.util.List<HandlerMethodArgumentResolver> resolvers) {
//    resolvers.add(testCurrentUserArgumentResolver());
//  }
//}
