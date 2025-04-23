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
import com.musinsam.shopservice.application.dto.request.ReqShopGetSearchDtoApiV1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class ShopInternalControllerApiV1Test {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testInternalGetShopList_Success() throws Exception {

    mockMvc.perform(
            RestDocumentationRequestBuilders.get("/internal/v1/shops")
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_COMPANY")
                .contentType("application/json")
                .param("page", "0")
                .param("size", "10")
                .param("orderby", "CREATED")
                .param("sort", "DESC")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentationWrapper.document("상점 전체 조회 성공 (내부용)",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Shop v1")
                    .summary("상점 전체 조회 (내부용)")
                    .description("상점 정보를 페이지네이션으로 전체 조회합니다. (내부용)")
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
                        fieldWithPath("shopPage").description("상점 페이지 객체"),
                        fieldWithPath("shopPage.content").description("상점 리스트"),
                        fieldWithPath("shopPage.content[].id").description("상점 ID"),
                        fieldWithPath("shopPage.content[].userId").description("상점 회원 ID"),
                        fieldWithPath("shopPage.content[].name").description("상점 이름"),
                        fieldWithPath("shopPage.page").description("페이지 정보"),
                        fieldWithPath("shopPage.page.size").description("페이지 크기"),
                        fieldWithPath("shopPage.page.number").description("페이지 번호"),
                        fieldWithPath("shopPage.page.totalElements").description("전체 데이터 수"),
                        fieldWithPath("shopPage.page.totalPages").description("전체 페이지 수")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  public void testInternalGetShop_Success() throws Exception {
    String id = "af80aa19-9c74-49dc-b115-8f4533de0f7b";

    mockMvc.perform(
            RestDocumentationRequestBuilders.get("/internal/v1/shops/{id}", id)
                .contentType("application/json")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentationWrapper.document("상점 단건 조회 성공 (내부용)",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Shop v1")
                    .summary("상점 단건 조회 (내부용)")
                    .description("상점 정보를 단건 조회합니다. (내부용)")
                    .responseFields(
                        fieldWithPath("shop.id").description("상점 ID"),
                        fieldWithPath("shop.userId").description("상점 회원 ID"),
                        fieldWithPath("shop.name").description("상점 이름")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  public void testInternalGetShopListSearch_Success() throws Exception {
    ReqShopGetSearchDtoApiV1 request = ReqShopGetSearchDtoApiV1.builder()
        .shop(
            ReqShopGetSearchDtoApiV1.Shop.builder()
                .name("nike")
                .build()
        )
        .build();

    mockMvc.perform(
            RestDocumentationRequestBuilders.get("/internal/v1/shops/search")
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_COMPANY")
                .contentType("application/json")
                .param("page", "0")
                .param("size", "10")
                .param("orderby", "CREATED")
                .param("sort", "DESC")
                .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentationWrapper.document("상점 검색 성공 (내부용)",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Shop v1")
                    .summary("상점 검색 (내부용)")
                    .description("상점 정보를 페이지네이션으로 검색합니다. (내부용)")
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
                        fieldWithPath("shopPage").description("상점 페이지 객체"),
                        fieldWithPath("shopPage.content").description("상점 리스트"),
                        fieldWithPath("shopPage.content[].id").description("상점 ID"),
                        fieldWithPath("shopPage.content[].userId").description("상점 회원 ID"),
                        fieldWithPath("shopPage.content[].name").description("상점 이름"),
                        fieldWithPath("shopPage.page").description("페이지 정보"),
                        fieldWithPath("shopPage.page.size").description("페이지 크기"),
                        fieldWithPath("shopPage.page.number").description("페이지 번호"),
                        fieldWithPath("shopPage.page.totalElements").description("전체 데이터 수"),
                        fieldWithPath("shopPage.page.totalPages").description("전체 페이지 수")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  public void testInternalCouponGetByShopId_Success() throws Exception {
    String id = "af80aa19-9c74-49dc-b115-8f4533de0f7b";

    mockMvc.perform(
            RestDocumentationRequestBuilders.get("/internal/v1/shops/coupons?shopId={id}", id)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_COMPANY")
                .contentType("application/json")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentationWrapper.document("상점에 적용된 쿠폰 조회 성공 (내부용)",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Shop v1")
                    .summary("상점에 적용된 쿠폰 조회 (내부용)")
                    .description("상점에 적용된 쿠폰을 페이지네이션으로 조회합니다.")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("요청자 ID"),
                        headerWithName("X-USER-ROLE").description("요청자 역할")
                    )
                    .responseFields(
                        fieldWithPath("couponList").description("쿠폰 목록")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  public void testInternalExistsShopById_Success() throws Exception {
    String id = "af80aa19-9c74-49dc-b115-8f4533de0f7b";

    mockMvc.perform(
            RestDocumentationRequestBuilders.get("/internal/v1/shops/{id}/exists", id)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_COMPANY")
                .contentType("application/json")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(
            MockMvcRestDocumentationWrapper.document("상점 존재 여부 조회 성공 (내부용)",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Shop v1")
                    .summary("상점 존재 여부 조회 (내부용)")
                    .description("상점 존재 여부를 조회합니다.")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("요청자 ID"),
                        headerWithName("X-USER-ROLE").description("요청자 역할")
                    )
                    .build()
                )
            )
        );
  }
}