package com.musinsam.alarmservice.presentation.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.alarmservice.application.dto.request.ReqAlarmPostDtoApiV1;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@ActiveProfiles("dev")
public class AlarmControllerApiV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testPostBy_Success() throws Exception {
    ReqAlarmPostDtoApiV1.Alarm alarm = ReqAlarmPostDtoApiV1.Alarm.builder().message("테스트 알림 메시지 생성")
        .build();
    mockMvc.perform(
            RestDocumentationRequestBuilders.post("/v1/slack-alarms")
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_USER")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(alarm))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").value("The alarm was successfully sent."))
        .andExpect(jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testGetBy_Success() throws Exception {

    mockMvc.perform(
            RestDocumentationRequestBuilders.get("/v1/slack-alarms")
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_USER")
                .contentType("application/json")
                .param("page", "0")
                .param("size", "10")
                .param("orderby", "CREATED")
                .param("sort", "DESC")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").value("Alarm full query succeeded."))
        .andExpect(jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testGetById_Success() throws Exception {
    UUID id = UUID.randomUUID();

    mockMvc.perform(
            RestDocumentationRequestBuilders.get("/v1/slack-alarms/{id}", id)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_USER")
                .contentType("application/json")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").value("Alarm single query was successful."))
        .andExpect(jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }
}

