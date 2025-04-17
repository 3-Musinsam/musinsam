package com.musinsam.paymentservice.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostApproveDtoApiV1;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostCancelDtoApiV1;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostInitDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentGetByIdDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostApproveDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostCancelDtoApiV1;
import com.musinsam.paymentservice.application.service.PaymentServiceApiV1;
import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
import com.musinsam.paymentservice.domain.payment.vo.PaymentStatus;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("결제 API")
@ActiveProfiles("test")
@WebMvcTest(PaymentControllerApiV1.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class PaymentControllerApiV1Tests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private PaymentServiceApiV1 paymentService;

  @Test
  @DisplayName("결제 초기화 성공 테스트")
  void initPaymentSuccess() throws Exception {
    // Given
    UUID orderId = UUID.randomUUID();

    ReqPaymentPostInitDtoApiV1 requestDto = ReqPaymentPostInitDtoApiV1.builder()
        .orderId(orderId)
        .build();

    // When & Then
    mockMvc.perform(post("/v1/payments/init")
            .header("X-USER-ID", 1L)
            .header("X-USER-ROLE", "ROLE_USER")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("payment-init-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Payment V1")
                    .summary("결제 초기화 성공")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("사용자 ID"),
                        headerWithName("X-USER-ROLE").description("사용자 권한")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  @DisplayName("결제 승인 성공 테스트")
  void approvePaymentSuccess() throws Exception {
    // Given
    UUID orderId = UUID.randomUUID();
    UUID paymentId = UUID.randomUUID();
    String paymentKey = "test_payment_key_12345";
    BigDecimal amount = new BigDecimal("90000");

    ReqPaymentPostApproveDtoApiV1 requestDto = ReqPaymentPostApproveDtoApiV1.builder()
        .paymentApproval(ReqPaymentPostApproveDtoApiV1.PaymentApproval.builder()
            .paymentKey(paymentKey)
            .orderId(orderId.toString())
            .amount(amount)
//            .responseCode("0000")
//            .responseMessage("Success")
//            .metadata("Additional data")
            .build())
        .build();

    ResPaymentPostApproveDtoApiV1 responseDto = ResPaymentPostApproveDtoApiV1.builder()
        .payment(ResPaymentPostApproveDtoApiV1.Payment.builder()
            .orderId(String.valueOf(orderId))
            .status(PaymentStatus.READY)
            .build())
        .build();

    // When & Then
    mockMvc.perform(post("/v1/payments/approve")
            .header("X-USER-ID", 1L)
            .header("X-USER-ROLE", "ROLE_USER")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("payment-approve-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Payment V1")
                    .summary("결제 승인 성공")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("사용자 ID"),
                        headerWithName("X-USER-ROLE").description("사용자 권한")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  @DisplayName("결제 상세 조회 성공 테스트")
  void getPaymentByIdSuccess() throws Exception {
    // Given
    UUID paymentId = UUID.randomUUID();
    UUID orderId = UUID.randomUUID();
    Long userId = 1L;

    ResPaymentGetByIdDtoApiV1 responseDto = ResPaymentGetByIdDtoApiV1.builder()
        .payment(ResPaymentGetByIdDtoApiV1.Payment.builder()
            .id(paymentId)
            .orderId(orderId)
            .userId(userId)
            .paymentStatus("PAID")
            .totalAmount(new BigDecimal("100000"))
            .createdAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .build())
        .build();

    // When & Then
    mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/payments/{paymentId}", paymentId)
            .header("X-USER-ID", userId)
            .header("X-USER-ROLE", "ROLE_USER")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("payment-getById-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Payment V1")
                    .summary("결제 상세 조회 성공")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("사용자 ID"),
                        headerWithName("X-USER-ROLE").description("사용자 권한")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  @DisplayName("결제 목록 조회 테스트")
  void getPaymentsSuccess() throws Exception {
    // Given
    Long userId = 1L;
    UUID paymentId = UUID.randomUUID();

    Pageable pageable = PageRequest.of(0, 10);

    PaymentEntity mockPaymentEntity1 = mock(PaymentEntity.class);
    PaymentEntity mockPaymentEntity2 = mock(PaymentEntity.class);

    List<PaymentEntity> mockPaymentEntities = List.of(
        mockPaymentEntity1,
        mockPaymentEntity2
    );

    Page<PaymentEntity> paymentEntityPage = new PageImpl<>(mockPaymentEntities, pageable,
        mockPaymentEntities.size());

    // When & Then
    mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/payments")
            .header("X-USER-ID", userId)
            .header("X-USER-ROLE", "ROLE_MASTER")
            .param("page", "0")
            .param("size", "10")
            .param("sort", "createdAt,desc")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("payment-get-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Payment V1")
                    .summary("결제 목록 조회 성공")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("사용자 ID"),
                        headerWithName("X-USER-ROLE").description("사용자 권한")
                    )
                    .queryParameters(
                        parameterWithName("paymentId").description("결제 ID").optional(),
                        parameterWithName("page").description("페이지 번호").optional(),
                        parameterWithName("size").description("페이지 크기").optional(),
                        parameterWithName("sort").description("정렬 기준 (형식: 필드명,정렬방향)").optional()
                    )
                    .build()
                )
            )
        );
  }

  @Test
  @DisplayName("결제 취소 성공 테스트")
  void cancelPaymentSuccess() throws Exception {
    // Given
    UUID paymentId = UUID.randomUUID();
    String cancelReason = "고객 요청에 의한 취소";
    BigDecimal cancelAmount = new BigDecimal("90000");

    ReqPaymentPostCancelDtoApiV1 requestDto = ReqPaymentPostCancelDtoApiV1.builder()
        .paymentCancel(ReqPaymentPostCancelDtoApiV1.PaymentCancel.builder()
            .cancelReason(cancelReason)
            .cancelAmount(cancelAmount)
            .build())
        .build();

    ResPaymentPostCancelDtoApiV1 responseDto = ResPaymentPostCancelDtoApiV1.builder()
        .payment(ResPaymentPostCancelDtoApiV1.Payment.builder()
            .paymentId(paymentId)
            .status(String.valueOf(PaymentStatus.CANCELED))
            .cancelReason(cancelReason)
            .canceledAt(ZonedDateTime.parse(ZonedDateTime.now().toString()))
            .build())
        .build();

    // When & Then
    mockMvc.perform(
            RestDocumentationRequestBuilders.post("/v1/payments/{paymentId}/cancel", paymentId)
                .header("X-USER-ID", 1L)
                .header("X-USER-ROLE", "ROLE_USER")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("payment-cancel-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Payment V1")
                    .summary("결제 취소 성공")
                    .requestHeaders(
                        headerWithName("X-USER-ID").description("사용자 ID"),
                        headerWithName("X-USER-ROLE").description("사용자 권한")
                    )
                    .build()
                )
            )
        );
  }
}
