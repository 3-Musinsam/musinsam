package com.musinsam.orderservice.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.orderservice.application.dto.request.ReqOrderPatchDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1.Order;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1.Order.OrderItem;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1.Order.ShippingInfo;
import com.musinsam.orderservice.application.dto.response.ResOrderGetByIdDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderGetDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPatchDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostDtoApiV1;
import com.musinsam.orderservice.application.service.OrderServiceApiV1;
import com.musinsam.orderservice.domain.order.entity.OrderEntity;
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

@DisplayName("주문 API")
@ActiveProfiles("test")
@WebMvcTest(OrderControllerApiV1.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class OrderControllerApiV1Tests {
  // TODO: 모든 테스트 MockMvcRestDocumentationWrapper.document 작성

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private OrderServiceApiV1 orderService;

  @Test
  @DisplayName("주문 생성 성공 테스트")
  void createOrderSuccess() throws Exception {
    // Given
    OrderItem orderItem1 = OrderItem.builder()
        .productId(UUID.randomUUID())
        .productName("ProductName1")
        .price(new BigDecimal("10000"))
        .quantity(5)
        .build();

    OrderItem orderItem2 = OrderItem.builder()
        .productId(UUID.randomUUID())
        .productName("ProductName2")
        .price(new BigDecimal("20000"))
        .quantity(10)
        .build();

    ShippingInfo shippingInfo = ShippingInfo.builder()
        .address("Shipping Address")
        .addressDetail("Shipping Address Detail")
        .receiverName("John Doe")
        .receiverPhone("010-1234-5678")
        .zipCode("12345")
        .build();

    ReqOrderPostDtoApiV1 requestDto = ReqOrderPostDtoApiV1.builder()
        .order(Order.builder()
            .orderItems(List.of(orderItem1, orderItem2))
            .totalAmount(new BigDecimal("250000"))
            .discountAmount(new BigDecimal("25000"))
            .finalAmount(new BigDecimal("225000"))
            .shippingInfo(shippingInfo)
            .request("Request Example")
            .couponId(UUID.randomUUID())
            .build()
        )
        .build();

    ResOrderPostDtoApiV1 responseDto = ResOrderPostDtoApiV1.builder()
        .order(ResOrderPostDtoApiV1.Order.builder()
            .orderStatus("PENDING")
            .createdAt(ZonedDateTime.now())
            .build())
        .build();

    when(orderService.createOrder(any(ReqOrderPostDtoApiV1.class), any()))
        .thenReturn(responseDto);

    // When & Then
    this.mockMvc.perform(post("/v1/orders")
            .header("X-USER-ID", 1L)
            .header("X-USER-ROLE", "ROLE_USER")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("order-create-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(ResourceSnippetParameters.builder()
                    .tag("Order Post Success API")
                    .summary("주문 생성 성공")
                    .requestSchema(Schema.schema("ReqOrderPostDtoApiV1"))
                    .responseSchema(Schema.schema("ResOrderPostDtoApiV1"))
                    .requestFields(
                        fieldWithPath("order").description("주문 정보"),
                        fieldWithPath("order.orderItems").description("주문 상품 목록"),
                        fieldWithPath("order.orderItems[].productId").description("상품 ID"),
                        fieldWithPath("order.orderItems[].productName").description("상품명"),
                        fieldWithPath("order.orderItems[].price").description("상품 가격"),
                        fieldWithPath("order.orderItems[].quantity").description("상품 수량"),
                        fieldWithPath("order.shippingInfo").description("배송 정보"),
                        fieldWithPath("order.shippingInfo.receiverName").description("수령인 이름"),
                        fieldWithPath("order.shippingInfo.receiverPhone").description(
                            "수령인 연락처"),
                        fieldWithPath("order.shippingInfo.zipCode").description("우편번호"),
                        fieldWithPath("order.shippingInfo.address").description("주소"),
                        fieldWithPath("order.shippingInfo.addressDetail").description("상세 주소"),
                        fieldWithPath("order.request").description("요청사항").optional(),
                        fieldWithPath("order.couponId").description("쿠폰 ID"),
                        fieldWithPath("order.totalAmount").description("총 주문 금액"),
                        fieldWithPath("order.discountAmount").description("할인 금액").optional(),
                        fieldWithPath("order.finalAmount").description("최종 결제 금액").optional()
                    )
                    .responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("message").description("응답 메시지"),
                        fieldWithPath("data").description("응답 데이터"),
                        fieldWithPath("data.order").description("주문 정보"),
                        fieldWithPath("data.order.id").description("주문 ID").optional(),
                        fieldWithPath("data.order.userId").description("사용자 ID").optional(),
                        fieldWithPath("data.order.orderStatus").description("주문 상태"),
                        fieldWithPath("data.order.orderItems").description("주문 상품 목록").optional(),
                        fieldWithPath("data.order.totalAmount").description("총 주문 금액").optional(),
                        fieldWithPath("data.order.discountAmount").description("할인 금액").optional(),
                        fieldWithPath("data.order.finalAmount").description("최종 결제 금액").optional(),
                        fieldWithPath("data.order.request").description("요청 사항").optional(),
                        fieldWithPath("data.order.createdAt").description("주문 생성 일시")
                    )
                    .build()
                )
            )
        );
  }

  @Test
  @DisplayName("주문 상세 조회 성공 테스트")
  void getOrderByIdSuccess() throws Exception {
    // Given
    UUID orderId = UUID.randomUUID();
    Long userId = 1L;

    ResOrderGetByIdDtoApiV1 responseDto = ResOrderGetByIdDtoApiV1.builder()
        .order(ResOrderGetByIdDtoApiV1.Order.builder()
            .id(orderId)
            .userId(userId)
            .orderStatus("PAID")
            .totalAmount(new BigDecimal("250000"))
            .discountAmount(new BigDecimal("25000"))
            .finalAmount(new BigDecimal("225000"))
            .request("배송 요청사항")
            .createdAt(ZonedDateTime.now())
            .orderItems(List.of(
                ResOrderGetByIdDtoApiV1.Order.OrderItem.builder()
                    .productId(UUID.randomUUID())
                    .productName("상품명1")
                    .price(new BigDecimal("10000"))
                    .quantity(5)
                    .finalAmount(new BigDecimal("50000"))
                    .build()
            ))
            .shippingInfo(ResOrderGetByIdDtoApiV1.Order.ShippingInfo.builder()
                .address("배송지 주소")
                .addressDetail("상세 주소")
                .receiverName("수령인")
                .receiverPhone("010-1234-5678")
                .zipCode("12345")
                .build())
            .build())
        .build();

    when(orderService.getOrder(orderId, userId)).thenReturn(responseDto);

    // When & Then
    mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/orders/{orderId}", orderId)
            .header("X-USER-ID", 1L)
            .header("X-USER-ROLE", "ROLE_USER")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("order-getById-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource("주문 상세 조회 성공")
            )
        )
    ;
  }

  @Test
  @DisplayName("주문 목록 조회 테스트")
  void getOrderSuccess() throws Exception {
    // Given
    Long userId = 1L;
    String query = "Sample Query";
    Pageable pageable = PageRequest.of(0, 10);

    OrderEntity mockOrderEntity1 = mock(OrderEntity.class);
    OrderEntity mockOrderEntity2 = mock(OrderEntity.class);

    List<OrderEntity> mockOrderEntity = List.of(
        mockOrderEntity1,
        mockOrderEntity2
    );

    Page<OrderEntity> orderEntityPage = new PageImpl<>(mockOrderEntity, pageable,
        mockOrderEntity.size());

    ResOrderGetDtoApiV1 responseDto = ResOrderGetDtoApiV1.of(orderEntityPage);

    when(orderService.getOrderList(any(Pageable.class), eq(query), eq(userId))).thenReturn(
        responseDto);

    // When & Then
    mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/orders")
            .header("X-USER-ID", 1L)
            .header("X-USER-ROLE", "ROLE_USER")
            .param("q", query)
            .param("page", "0")
            .param("size", "10")
            .param("sort", "createdAt,desc")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("order-get-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource("주문 목록 조회 성공")
            )
        );
  }

  @Test
  @DisplayName("주문 수정 성공 테스트")
  void updateOrderSuccess() throws Exception {
    // Given
    UUID orderId = UUID.randomUUID();

    ReqOrderPatchDtoApiV1 requestDto = ReqOrderPatchDtoApiV1.builder()
        .order(ReqOrderPatchDtoApiV1.Order.builder()
            .request("Sample Request Message")
            .build())
        .build();

    ResOrderPatchDtoApiV1 responseDto = ResOrderPatchDtoApiV1.builder()
        .order(ResOrderPatchDtoApiV1.Order.builder()
            .id(orderId)
            .request("Sample Request Message")
            .updatedAt(ZonedDateTime.now())
            .build())
        .build();

    when(orderService.updateOrder(
        any(UUID.class),
        any(ReqOrderPatchDtoApiV1.class),
        any(Long.class))
    ).thenReturn(responseDto);

    // When & Then
    mockMvc.perform(RestDocumentationRequestBuilders.patch("/v1/orders/{orderId}", orderId)
            .header("X-USER-ID", 1L)
            .header("X-USER-ROLE", "ROLE_USER")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("order-patch-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource("주문 수정 성공")
            )
        );
  }

  @Test
  @DisplayName("주문 취소 성공 테스트")
  void cancelOrderSuccess() throws Exception {
    // Given
    UUID orderId = UUID.randomUUID();

    ReqOrderPostCancelDtoApiV1 requestDto = ReqOrderPostCancelDtoApiV1.builder()
        .cancelReason("Sample Cancel Reason")
        .build();

    ResOrderPostCancelDtoApiV1 responseDto = ResOrderPostCancelDtoApiV1.builder()
        .order(ResOrderPostCancelDtoApiV1.Order.builder()
            .id(orderId)
            .orderStatus("CANCELLED")
            .cancelReason("Sample Cancel Reason")
            .canceledAt(ZonedDateTime.now())
            .build())
        .build();

    when(orderService.cancelOrder(
        any(UUID.class),
        any(ReqOrderPostCancelDtoApiV1.class),
        any(Long.class)))
        .thenReturn(responseDto);

    // When & Then
    mockMvc.perform(RestDocumentationRequestBuilders.post("/v1/orders/{orderId}/cancel", orderId)
            .header("X-USER-ID", 1L)
            .header("X-USER-ROLE", "ROLE_USER")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("order-cancel-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource("주문 취소 성공")
            )
        );
  }
}
