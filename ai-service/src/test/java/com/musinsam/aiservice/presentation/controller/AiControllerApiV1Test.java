package com.musinsam.aiservice.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.aiservice.application.dto.request.ReqAiPostDtoApiV1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureRestDocs
@ActiveProfiles
@AutoConfigureMockMvc(addFilters = false)
class AiControllerApiV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Value("${openai.api.key}")
  private String apiKey;

  @Test
  public void testPostBy_Success() throws Exception {

    ReqAiPostDtoApiV1 request = ReqAiPostDtoApiV1.builder()
        .ai(
            ReqAiPostDtoApiV1.Ai.builder()
                .prompt(
                    "Name: John Doe\nEmail: johndoe@example.com\nPhone Number: 555-555-5555\n Order Number: 1234\nOrder Total: $50.00\n 100자 이내로 주문 알림 생성해줘. \n\n")
                .build()
        )
        .build();

    mockMvc.perform(
            RestDocumentationRequestBuilders.post("/v1/ais")
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_USER")
                .header("Authorization", "Bearer " + apiKey)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message")
                .value("The ai message was successfully sent."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andDo(MockMvcRestDocumentationWrapper.document("알림 등록 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Ai v1")
                    .summary("Ai 메시지 등록")
                    .description("Ai 메시지를 생성합니다.")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("요청자 ID"),
                        headerWithName("X-USER-ROLE").description("요청자 역할")
                    )
                    .requestFields(
                        fieldWithPath("ai").description("Ai 요청 정보").type(JsonFieldType.OBJECT),
                        fieldWithPath("ai.prompt").description("Ai 메시지 요청 내용")
                            .type(JsonFieldType.STRING)
                    )
                    .responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data").optional().description("응답 데이터")
                    )
                    .build()
                )
            )
        );
  }
}