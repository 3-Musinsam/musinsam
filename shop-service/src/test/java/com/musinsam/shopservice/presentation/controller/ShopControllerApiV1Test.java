package com.musinsam.shopservice.presentation.controller;

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
import com.musinsam.shopservice.application.dto.request.ReqShopPostDtoApiV1;
import com.musinsam.shopservice.application.dto.request.ReqShopPutByShopIdDtoApiV1;
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
public class ShopControllerApiV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testPostBy_Success() throws Exception {
    ReqShopPostDtoApiV1 request = ReqShopPostDtoApiV1.builder()
        .shop(
            ReqShopPostDtoApiV1.Shop.builder()
                .userId(1L)
                .name("nike")
                .build()
        )
        .build();

    mockMvc.perform(
            RestDocumentationRequestBuilders.post("/v1/shops")
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_COMPANY")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("0"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상점 생성 성공"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("상점 등록 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Shop v1")
                    .summary("상점 등록")
                    .description("상점을 등록합니다.")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("요청자 ID"),
                        headerWithName("X-USER-ROLE").description("요청자 역할")
                    )
                    .requestFields(
                        fieldWithPath("shop").description("상점 요청 정보").type(JsonFieldType.OBJECT),
                        fieldWithPath("shop.userId").description("상점 회원 ID"),
                        fieldWithPath("shop.name").description("상점 이름")
                            .type(JsonFieldType.STRING)
                    )
                    .responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data.shop.id").optional().description("응답 데이터")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  public void testGetBy_Success() throws Exception {

    mockMvc.perform(
            RestDocumentationRequestBuilders.get("/v1/shops")
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_COMPANY")
                .contentType("application/json")
                .param("page", "0")
                .param("size", "10")
                .param("orderby", "CREATED")
                .param("sort", "DESC")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상점 목록 조회 성공"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("상점 목록 조회 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Shop v1")
                    .summary("상점 목록 조회")
                    .description("상점 전체 목록을 페이지네이션으로 조회합니다.")
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
                        fieldWithPath("data.shopPage").description("상점 페이지 객체"),
                        fieldWithPath("data.shopPage.content").description("상점 리스트"),
                        fieldWithPath("data.shopPage.content[].id").description("상점 ID"),
                        fieldWithPath("data.shopPage.content[].userId").description("상점 회원 ID"),
                        fieldWithPath("data.shopPage.content[].name").description("상점 이름"),
                        fieldWithPath("data.shopPage.page").description("페이지 정보"),
                        fieldWithPath("data.shopPage.page.size").description("페이지 크기"),
                        fieldWithPath("data.shopPage.page.number").description("페이지 번호"),
                        fieldWithPath("data.shopPage.page.totalElements").description("전체 데이터 수"),
                        fieldWithPath("data.shopPage.page.totalPages").description("전체 페이지 수")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  public void testGetById_Success() throws Exception {
    String id = "af80aa19-9c74-49dc-b115-8f4533de0f7b";

    mockMvc.perform(
            RestDocumentationRequestBuilders.get(
                    "/v1/shops/{id}", id)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_COMPANY")
                .contentType("application/json")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("상점 상세 조회 성공"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("상점 상세 조회 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Shop v1")
                    .summary("상점 단건 조회")
                    .description("상점 상세내역을 조회합니다.")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("요청자 ID"),
                        headerWithName("X-USER-ROLE").description("요청자 역할")
                    )
                    .responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data").description("응답 데이터"),
                        fieldWithPath("data.shop").description("상점 응답 정보")
                            .type(JsonFieldType.OBJECT),
                        fieldWithPath("data.shop.id").description("상점 ID"),
                        fieldWithPath("data.shop.userId").description("상점 회원 ID"),
                        fieldWithPath("data.shop.name").description("상점 이름")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  public void testPutByName_Success() throws Exception {
    String id = "af80aa19-9c74-49dc-b115-8f4533de0f7b";

    ReqShopPutByShopIdDtoApiV1 request = ReqShopPutByShopIdDtoApiV1.builder()
        .shop(
            ReqShopPutByShopIdDtoApiV1.Shop.builder()
                .name("adidas")
                .build()
        )
        .build();

    mockMvc.perform(
            RestDocumentationRequestBuilders.put(
                    "/v1/shops/{shopId}", id)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_COMPANY")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("상점 수정 성공"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("상점 수정 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Shop v1")
                    .summary("상점 수정")
                    .description("상점 정보를 수정합니다.")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("요청자 ID"),
                        headerWithName("X-USER-ROLE").description("요청자 역할")
                    )
                    .requestFields(
                        fieldWithPath("shop").description("상점 요청 정보").type(JsonFieldType.OBJECT),
                        fieldWithPath("shop.name").description("상점 이름").type(JsonFieldType.STRING)
                    )
                    .responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data").description("응답 데이터"),
                        fieldWithPath("data.shop").description("상점 응답 정보")
                            .type(JsonFieldType.OBJECT),
                        fieldWithPath("data.shop.id").description("상점 ID")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  public void testDeleteById_Success() throws Exception {
    String id = "af80aa19-9c74-49dc-b115-8f4533de0f7b";

    mockMvc.perform(
            RestDocumentationRequestBuilders.delete(
                    "/v1/shops/{id}", id)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_COMPANY")
                .contentType("application/json")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("상점 삭제 성공"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("상점 삭제 성공",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Shop v1")
                    .summary("상점 삭제")
                    .description("상점을 삭제합니다.")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("요청자 ID"),
                        headerWithName("X-USER-ROLE").description("요청자 역할")
                    )
                    .responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data").description("응답 데이터"),
                        fieldWithPath("data.shop").description("상점 응답 정보")
                            .type(JsonFieldType.OBJECT),
                        fieldWithPath("data.shop.id").description("상점 ID")
                    )
                    .build()
                )
            )
        );
  }
}
