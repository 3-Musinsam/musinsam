package com.musinsam.alarmservice.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.alarmservice.application.dto.request.ReqAlarmPostDtoApiV1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class AlarmControllerApiV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testPostBy_Success() throws Exception {
    ReqAlarmPostDtoApiV1 request = ReqAlarmPostDtoApiV1.builder()
        .alarm(
            ReqAlarmPostDtoApiV1.Alarm.builder()
                .message("테스트 알림 메시지 생성")
                .build()
        )
        .build();

    mockMvc.perform(
            RestDocumentationRequestBuilders.post("/v1/slack-alarms")
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_USER")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("The alarm was successfully sent."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("알림 등록 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Alarm v1")
                    .summary("알림 등록")
                    .description("슬랙 알림 메시지를 전송합니다.")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("요청자 ID"),
                        headerWithName("X-USER-ROLE").description("요청자 역할")
                    )
                    .requestFields(
                        fieldWithPath("alarm").description("알림 요청 정보").type(JsonFieldType.OBJECT),
                        fieldWithPath("alarm.message").description("알림 메시지 내용")
                            .type(JsonFieldType.STRING)
                    )
                    .responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data.alarm.id").optional().description("응답 데이터")
                    )
                    .build()
                )
            )
        );
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
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Alarm full query succeeded."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("알림 전체 조회 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Alarm v1")
                    .summary("알림 전체 조회")
                    .description("전체 알림 메시지를 페이지네이션으로 조회합니다.")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("요청자 ID"),
                        headerWithName("X-USER-ROLE").description("요청자 역할")
                    )
                    .queryParameters(
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("페이지 크기"),
                        parameterWithName("orderby").description("정렬 기준 필드"),
                        parameterWithName("sort").description("정렬 방식 (ASC/DESC)")
                    )
                    .responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data").description("응답 데이터"),
                        fieldWithPath("data.alarmPage").description("알림 페이지 객체"),
                        fieldWithPath("data.alarmPage.content").description("알림 리스트"),
                        fieldWithPath("data.alarmPage.content[].id").description("알림 ID"),
                        fieldWithPath("data.alarmPage.content[].message").description("알림 메시지"),
                        fieldWithPath("data.alarmPage.page").description("페이지 정보"),
                        fieldWithPath("data.alarmPage.page.size").description("페이지 크기"),
                        fieldWithPath("data.alarmPage.page.number").description("페이지 번호"),
                        fieldWithPath("data.alarmPage.page.totalElements").description("전체 데이터 수"),
                        fieldWithPath("data.alarmPage.page.totalPages").description("전체 페이지 수")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  public void testGetById_Success() throws Exception {
    String id = "4d23976f-94cc-4bbb-80b1-4833b58d3b46";
    
    mockMvc.perform(
            RestDocumentationRequestBuilders.get(
                    "/v1/slack-alarms/{id}", id)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_USER")
                .contentType("application/json")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("Alarm single query was successful."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("알림 단건 조회 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Alarm v1")
                    .summary("알림 단건 조회")
                    .description("단건 알림 메시지를 알림 ID로 조회합니다.")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("요청자 ID"),
                        headerWithName("X-USER-ROLE").description("요청자 역할")
                    )
                    .responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data").description("응답 데이터"),
                        fieldWithPath("data.alarm").description("알림 요청 정보")
                            .type(JsonFieldType.OBJECT),
                        fieldWithPath("data.alarm.id").description("알림 ID"),
                        fieldWithPath("data.alarm.message").description("알림 메시지")
                    )
                    .build()
                )
            )
        );
  }
}

