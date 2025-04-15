package com.musinsam.eventservice.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.eventservice.application.dto.request.ReqEventPostDtoApiV1;
import com.musinsam.eventservice.application.dto.request.ReqEventPostDtoApiV1.Event;
import com.musinsam.eventservice.application.dto.request.ReqEventPutByEventIdDtoApiV1;
import com.musinsam.eventservice.application.dto.request.ReqEventPutByEventIdDtoApiV1.UpdatedEvent;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
class EventControllerApiV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("이벤트 등록 성공")
  public void testEventPostSuccess() throws Exception {

    ReqEventPostDtoApiV1 reqDto = ReqEventPostDtoApiV1.builder()
        .event(Event.builder()
            .name("할인이벤트")
            .startTime(ZonedDateTime.of(2023, 5, 15, 9, 0, 0, 0,
                ZoneId.of("Asia/Seoul"))) // 2023년 5월 15일 오전 9시
            .endTime(ZonedDateTime.of(2023, 5, 30, 18, 0, 0, 0,
                ZoneId.of("Asia/Seoul"))) // 2023년 5월 30일 오후 6시
            .maxPurchase(100)
            .build())
        .build();

    String dtoJson = objectMapper.writeValueAsString(reqDto);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/events")
                .content(dtoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_MASTER")
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$.code").value(0),
            MockMvcResultMatchers.jsonPath("$.message").value("이벤트 생성 성공"),
            MockMvcResultMatchers.jsonPath("$.data").doesNotExist()
        )
        .andDo(
            MockMvcResultHandlers.print()
        );
  }

  @Test
  @DisplayName("이벤트 목록 조회 성공")
  public void testEventGetSuccess() throws Exception {

    mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/events")
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_MASTER")
                .param("active", "true")
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$.code").value(0),
            MockMvcResultMatchers.jsonPath("$.message").value("이벤트 목록 조회 성공"),
            MockMvcResultMatchers.jsonPath("$.data").doesNotExist()
        )
        .andDo(
            MockMvcResultHandlers.print()
        );
  }

  @Test
  @DisplayName("이벤트 수정 성공")
  public void testEventPutByIdSuccess() throws Exception {

    UUID eventId = UUID.randomUUID();

    ReqEventPutByEventIdDtoApiV1 reqDto = ReqEventPutByEventIdDtoApiV1.builder()
        .updatedEvent(UpdatedEvent.builder()
            .name("수정된 이벤트 이름")
            .startTime(ZonedDateTime.of(2023, 5, 15, 9, 0, 0, 0,
                ZoneId.of("Asia/Seoul"))) // 2023년 5월 15일 오전 9시
            .endTime(ZonedDateTime.of(2023, 5, 30, 18, 0, 0, 0,
                ZoneId.of("Asia/Seoul"))) // 2023년 5월 30일 오후 6시
            .maxPurchase(50)
            .build())
        .build();

    String dtoJson = objectMapper.writeValueAsString(reqDto);

    mockMvc.perform(
            MockMvcRequestBuilders.put("/v1/events/{eventId}", eventId)
                .content(dtoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_MASTER")
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$.code").value(0),
            MockMvcResultMatchers.jsonPath("$.message").value("이벤트 수정 성공"),
            MockMvcResultMatchers.jsonPath("$.data").doesNotExist()
        )
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("이벤트 수정 실패 - 이름 누락")
  public void testEventPutByIdFail() throws Exception {

    UUID eventId = UUID.randomUUID();

    ReqEventPutByEventIdDtoApiV1 reqDto = ReqEventPutByEventIdDtoApiV1.builder()
        .updatedEvent(UpdatedEvent.builder()
            .name("")
            .startTime(ZonedDateTime.of(2023, 5, 15, 9, 0, 0, 0,
                ZoneId.of("Asia/Seoul"))) // 2023년 5월 15일 오전 9시
            .endTime(ZonedDateTime.of(2023, 5, 30, 18, 0, 0, 0,
                ZoneId.of("Asia/Seoul"))) // 2023년 5월 30일 오후 6시
            .maxPurchase(50)
            .build())
        .build();

    String dtoJson = objectMapper.writeValueAsString(reqDto);

    mockMvc.perform(
            MockMvcRequestBuilders.put("/v1/events/{eventId}", eventId)
                .content(dtoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_MASTER")
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isBadRequest(),
            MockMvcResultMatchers.jsonPath("$.code").value(-1),
            MockMvcResultMatchers.jsonPath("$.message").value("Invalid input value."),
            MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").value("updatedEvent.name"),
            MockMvcResultMatchers.jsonPath("$.fieldErrors[0].value").value(""),
            MockMvcResultMatchers.jsonPath("$.fieldErrors[0].reason").value("이벤트 이름을 입력해주세요.")
        )
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("이벤트 삭제 성공")
  public void testEventDeleteSuccess() throws Exception {

    UUID eventId = UUID.randomUUID();

    mockMvc.perform(
            MockMvcRequestBuilders.delete("/v1/events/{eventId}", eventId)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_MASTER")
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$.code").value(0),
            MockMvcResultMatchers.jsonPath("$.message").value("이벤트 삭제 성공"),
            MockMvcResultMatchers.jsonPath("$.data").doesNotExist()
        )
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("이벤트 상세 조회 성공")
  public void testEventGetByEventIdSuccess() throws Exception {

    UUID eventId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/events/{eventId}",
                    eventId)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_COMPANY")
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$.code").value(0),
            MockMvcResultMatchers.jsonPath("$.message").value("이벤트 상세 조회 성공"),
            MockMvcResultMatchers.jsonPath("$.data").doesNotExist()
        )
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("이벤트 상품 조회 성공")
  public void TestEventGetProductByEventIdSuccess() throws Exception {

    UUID eventId = UUID.randomUUID();

    mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/events/{eventId}/products", eventId)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_COMPANY")
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            MockMvcResultMatchers.jsonPath("$.code").value(0),
            MockMvcResultMatchers.jsonPath("$.message").value("이벤트 상품 목록 조회 성공"),
            MockMvcResultMatchers.jsonPath("$.data").doesNotExist()
        )
        .andDo(
            MockMvcResultHandlers.print()
        );
  }

}