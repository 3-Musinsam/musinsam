package com.musinsam.userservice.app.presentation.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthGenerateTokenDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthLoginDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthLogoutDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthSignupDtoApiV1;
import com.musinsam.userservice.app.application.dto.v1.auth.request.ReqAuthValidateTokenDtoApiV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class AuthControllerApiV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("[Auth] 회원가입 성공")
  public void givenUserWhenSignUpThenSuccess() throws Exception {
    // given
    ReqAuthSignupDtoApiV1.User user = ReqAuthSignupDtoApiV1.User.builder()
        .email("foo@gmail.com")
        .password("qwerasdf1")
        .name("foo")
        .build();

    ReqAuthSignupDtoApiV1 requestDto = ReqAuthSignupDtoApiV1.builder()
        .user(user)
        .build();

    String json = objectMapper.writeValueAsString(requestDto);

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/auth/signup")
                .contentType("application/json")
                .content(json)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .value("User signup completed successfully.")) // USER_SIGNUP_SUCCESS.getMessage()
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[Auth] 로그인 성공")
  public void givenUserWhenLoginThenSuccess() throws Exception {
    // given
    ReqAuthLoginDtoApiV1.User user = ReqAuthLoginDtoApiV1.User.builder()
        .email("foo@gmail.com")
        .password("qwerasdf1")
        .build();

    ReqAuthLoginDtoApiV1 requestDto = ReqAuthLoginDtoApiV1.builder()
        .user(user)
        .build();

    String json = objectMapper.writeValueAsString(requestDto);

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/auth/login")
                .contentType("application/json")
                .content(json)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .value("User login completed successfully.")) // USER_LOGIN_SUCCESS.getMessage()
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[Auth] 로그아웃 성공")
  public void givenTokenWhenLogoutThenSuccess() throws Exception {
    // given
    ReqAuthLogoutDtoApiV1 reqAuthLogoutDtoApiV1 = ReqAuthLogoutDtoApiV1.builder()
        .token("fake-access-token")
        .build();

    String json = objectMapper.writeValueAsString(reqAuthLogoutDtoApiV1);

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/auth/logout")
                .contentType("application/json")
                .content(json)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .value("User logout completed successfully.")) // USER_LOGOUT_SUCCESS.getMessage()
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[Auth] 토큰 재발급 성공")
  void givenTokenWhenGenerateTokenThenSuccess() throws Exception {
    // given
    ReqAuthGenerateTokenDtoApiV1 reqAuthGenerateTokenDtoApiV1 = ReqAuthGenerateTokenDtoApiV1.builder()
        .userId(1L)
        .build();

    String json = objectMapper.writeValueAsString(reqAuthGenerateTokenDtoApiV1);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/auth/generate-token")
                .contentType("application/json")
                .content(json)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Token generated successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[Auth] 토큰 검증 성공")
  void givenTokenWhenValidateTokenThenSuccess() throws Exception {
    // given
    ReqAuthValidateTokenDtoApiV1 reqAuthGenerateTokenDtoApiV1 = ReqAuthValidateTokenDtoApiV1.builder()
        .token("fake-access-token").build();

    String json = objectMapper.writeValueAsString(reqAuthGenerateTokenDtoApiV1);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/auth/validate-token")
                .contentType("application/json")
                .content(json)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Token validated successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }
}
