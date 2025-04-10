package com.musinsam.userservice.app.presentation.v1;

import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;

import com.fasterxml.jackson.databind.ObjectMapper;
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
class UserControllerApiV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private static final String HEADER_USER_ID = "X-USER-ID";
  private static final String HEADER_USER_ROLE = "X-USER-ROLE";
  private static final String BASE_URL = "/v1/users";

  @Test
  @DisplayName("[User] 단일 사용자 조회 성공")
  public void givenUserId_whenGetUser_thenSuccess() throws Exception {
    // given
    Long userId = 1L;

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.get(BASE_URL + "/" + userId)
                .header(HEADER_USER_ID, "1")
                .header(HEADER_USER_ROLE, "ROLE_MASTER")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User retrieved successfully."))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[User] 사용자 삭제 성공")
  public void givenUserId_whenDeleteUser_thenSuccess() throws Exception {
    // given
    Long userId = 1L;

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.delete(BASE_URL + "/" + userId)
                .header(HEADER_USER_ID, 1)
                .header(HEADER_USER_ROLE, "ROLE_MASTER")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User deleted successfully by ID."))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[User] 전체 사용자 조회 성공")
  public void givenQueryParams_whenGetUsers_thenSuccess() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get(BASE_URL)
                .param("name", "홍길동")
                .header(HEADER_USER_ID, 9999)
                .header(HEADER_USER_ROLE, "ROLE_MASTER")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User list retrieved successfully."))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[User] 사용자 권한 변경 성공")
  public void givenUserId_whenPatchUserRole_thenSuccess() throws Exception {
    // given
    Long userId = 1L;

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.patch(BASE_URL + "/" + userId + "/role")
                .header(HEADER_USER_ID, 1)
                .header(HEADER_USER_ROLE, "ROLE_MASTER")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User role updated successfully by ID."))
        .andDo(MockMvcResultHandlers.print());
  }
}
