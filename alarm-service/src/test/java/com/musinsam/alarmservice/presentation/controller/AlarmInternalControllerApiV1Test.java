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
public class AlarmInternalControllerApiV1Test {

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
            RestDocumentationRequestBuilders.post("/internal/v1/slack-alarms")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentationWrapper.document("알림 등록 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Alarm v1")
                    .summary("알림 등록")
                    .description("슬랙 알림 메시지를 전송합니다.")
                    .requestFields(
                        fieldWithPath("alarm").description("알림 요청 정보").type(JsonFieldType.OBJECT),
                        fieldWithPath("alarm.message").description("알림 메시지 내용")
                            .type(JsonFieldType.STRING)
                    )
                    .responseFields(
                        fieldWithPath("alarm").description("알림 정보"),
                        fieldWithPath("alarm.id").optional().description("알림 ID")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  public void testGetBy_Success() throws Exception {

    mockMvc.perform(
            RestDocumentationRequestBuilders.get("/internal/v1/slack-alarms")
                .contentType("application/json")
                .param("page", "0")
                .param("size", "10")
                .param("orderby", "CREATED")
                .param("sort", "DESC")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentationWrapper.document("알림 전체 조회 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Alarm v1")
                    .summary("알림 전체 조회")
                    .description("전체 알림 메시지를 페이지네이션으로 조회합니다.")
                    .queryParameters(
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("페이지 크기"),
                        parameterWithName("orderby").description("정렬 기준 필드"),
                        parameterWithName("sort").description("정렬 방식 (ASC/DESC)")
                    )
                    .responseFields(
                        fieldWithPath("alarmPage").description("알림 페이지 객체"),
                        fieldWithPath("alarmPage.content").description("알림 리스트"),
                        fieldWithPath("alarmPage.content[].id").description("알림 ID"),
                        fieldWithPath("alarmPage.content[].message").description("알림 메시지"),
                        fieldWithPath("alarmPage.page").description("페이지 정보"),
                        fieldWithPath("alarmPage.page.size").description("페이지 크기"),
                        fieldWithPath("alarmPage.page.number").description("페이지 번호"),
                        fieldWithPath("alarmPage.page.totalElements").description("전체 데이터 수"),
                        fieldWithPath("alarmPage.page.totalPages").description("전체 페이지 수")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  public void testGetById_Success() throws Exception {
    String id = "76a5abec-741d-403d-b6c5-3a2f01f802ff";

    mockMvc.perform(
            RestDocumentationRequestBuilders.get("/internal/v1/slack-alarms/{id}", id)
                .contentType("application/json")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentationWrapper.document("알림 단건 조회 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Alarm v1")
                    .summary("알림 단건 조회")
                    .description("단건 알림 메시지를 알림 ID로 조회합니다.")
                    .responseFields(
                        fieldWithPath("alarm").description("알림 정보")
                            .type(JsonFieldType.OBJECT),
                        fieldWithPath("alarm.id").description("알림 ID"),
                        fieldWithPath("alarm.message").description("알림 메시지")
                    )
                    .build()
                )
            )
        );
  }
}
