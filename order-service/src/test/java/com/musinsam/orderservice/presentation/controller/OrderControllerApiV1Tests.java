package com.musinsam.orderservice.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1.Order;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1.Order.OrderItem;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1.Order.ShippingInfo;
import com.musinsam.orderservice.application.dto.request.ReqOrderPutDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderGetDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPutDtoApiV1;
import com.musinsam.orderservice.application.service.OrderServiceApiV1;
import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.vo.OrderCancelType;
import com.musinsam.orderservice.domain.order.vo.OrderStatus;
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
        .id(UUID.randomUUID())
        .productName("ProductName1")
        .price(new BigDecimal("10000"))
        .quantity(5)
        .build();

    OrderItem orderItem2 = OrderItem.builder()
        .id(UUID.randomUUID())
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
            .request("Request Example").couponId(UUID.randomUUID())
            .build())
        .build();

    ResOrderPostDtoApiV1 responseDto = ResOrderPostDtoApiV1.builder()
        .order(ResOrderPostDtoApiV1.Order.builder()
            .id(UUID.randomUUID())
            .userId(1L)
            .orderStatus("PENDING")
            .orderItems(
                List.of(ResOrderPostDtoApiV1.Order.OrderItem.builder()
                        .productId(UUID.randomUUID())
                        .productName("ProductName1")
                        .price(new BigDecimal("10000"))
                        .quantity(5)
                        .build(),
                    ResOrderPostDtoApiV1.Order.OrderItem.builder()
                        .productId(UUID.randomUUID())
                        .productName("ProductName2")
                        .price(new BigDecimal("20000"))
                        .quantity(10)
                        .build()))
            .totalAmount(new BigDecimal("250000"))
            .discountAmount(new BigDecimal("25000"))
            .finalAmount(new BigDecimal("225000"))
            .request("Request Example")
            .createdAt(ZonedDateTime.now())
            .build())
        .build();

    when(orderService.createOrder(any(ReqOrderPostDtoApiV1.class), any())).thenReturn(responseDto);

    // When & Then
    mockMvc.perform(post("/v1/orders")
            .header("X-USER-ID", 1L)
            .header("X-USER-ROLE", "ROLE_USER")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message")
            .exists())
        .andDo(print())
        .andDo(MockMvcRestDocumentationWrapper.document("order-create-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Order V1")
                        .summary("주문 생성 성공")
                        .requestSchema(Schema.schema("ReqOrderPostDtoApiV1"))
                        .responseSchema(Schema.schema("ResOrderPostDtoApiV1"))
                        .requestHeaders(
                            headerWithName("X-USER-ID").description("사용자 ID"),
                            headerWithName("X-USER-ROLE").description("사용자 권한"))
                        .requestFields(
                            fieldWithPath("order").description("주문 정보"),
                            fieldWithPath("order.orderItems").description("주문 상품 목록"),
                            fieldWithPath("order.orderItems[].id").description("상품 ID"),
                            fieldWithPath("order.orderItems[].productName").description("상품명"),
                            fieldWithPath("order.orderItems[].price").description("상품 가격"),
                            fieldWithPath("order.orderItems[].quantity").description("상품 수량"),
                            fieldWithPath("order.shippingInfo").description("배송 정보"),
                            fieldWithPath("order.shippingInfo.receiverName").description("수령인 이름"),
                            fieldWithPath("order.shippingInfo.receiverPhone").description("수령인 연락처"),
                            fieldWithPath("order.shippingInfo.zipCode").description("우편번호"),
                            fieldWithPath("order.shippingInfo.address").description("주소"),
                            fieldWithPath("order.shippingInfo.addressDetail").description("상세 주소"),
                            fieldWithPath("order.request").description("요청사항").optional(),
                            fieldWithPath("order.couponId").description("쿠폰 ID"),
                            fieldWithPath("order.totalAmount").description("총 주문 금액"),
                            fieldWithPath("order.discountAmount").description("할인 금액").optional(),
                            fieldWithPath("order.finalAmount").description("최종 결제 금액").optional())
                        .responseFields(fieldWithPath("code").description("응답 코드"),
                            fieldWithPath("message").description("응답 메시지"),
                            fieldWithPath("data").description("응답 데이터"),
                            fieldWithPath("data.order").description("주문 정보"),
                            fieldWithPath("data.order.id").description("주문 ID").optional(),
                            fieldWithPath("data.order.userId").description("사용자 ID").optional(),
                            fieldWithPath("data.order.orderStatus").description("주문 상태"),
                            fieldWithPath("data.order.orderItems").description("주문 상품 목록").optional(),
                            fieldWithPath("data.order.orderItems[].productId").description("상품 ID"),
                            fieldWithPath("data.order.orderItems[].productName").description("상품명"),
                            fieldWithPath("data.order.orderItems[].quantity").description("상품 수량"),
                            fieldWithPath("data.order.orderItems[].price").description("상품 가격"),

                            fieldWithPath("data.order.totalAmount").description("총 주문 금액").optional(),
                            fieldWithPath("data.order.discountAmount").description("할인 금액").optional(),
                            fieldWithPath("data.order.finalAmount").description("최종 결제 금액").optional(),
                            fieldWithPath("data.order.request").description("요청 사항").optional(),
                            fieldWithPath("data.order.createdAt").description("주문 생성 일시"))
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

    // When & Then
    mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/orders/{orderId}", orderId)
            .header("X-USER-ID", 1L)
            .header("X-USER-ROLE", "ROLE_USER")
            .param("orderId", String.valueOf(orderId))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("order-getById-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Order V1").summary("주문 상세 조회 성공")
                        .requestHeaders(
                            headerWithName("X-USER-ID").description("사용자 ID"),
                            headerWithName("X-USER-ROLE").description("사용자 권한"))
                        .build()
                )
            )
        );
  }

  @Test
  @DisplayName("주문 목록 조회 테스트")
  void getOrderSuccess() throws Exception {
    // Given
    Long userId = 1L;
    String query = "Sample Query";
    UUID orderId = UUID.randomUUID();
    Pageable pageable = PageRequest.of(0, 10);

    OrderEntity mockOrderEntity1 = mock(OrderEntity.class);
    OrderEntity mockOrderEntity2 = mock(OrderEntity.class);

    List<OrderEntity> mockOrderEntity = List.of(mockOrderEntity1, mockOrderEntity2);

    Page<OrderEntity> orderEntityPage = new PageImpl<>(mockOrderEntity, pageable,
        mockOrderEntity.size());

    ResOrderGetDtoApiV1 responseDto = ResOrderGetDtoApiV1.of(orderEntityPage);

    // When & Then
    mockMvc.perform(RestDocumentationRequestBuilders.get("/v1/orders")
            .header("X-USER-ID", 1L)
            .header("X-USER-ROLE", "ROLE_USER")
            .param("q", query).param("orderId", orderId.toString())
            .param("page", "0")
            .param("size", "10")
            .param("sort", "createdAt,desc")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(0))
        .andExpect(jsonPath("$.message").exists())
        .andDo(
            MockMvcRestDocumentationWrapper.document("order-get-success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Order V1")
                        .summary("주문 목록 조회 성공")
                        .requestHeaders(headerWithName("X-USER-ID").description("사용자 ID"),
                            headerWithName("X-USER-ROLE").description("사용자 권한"))
                        .queryParameters(
                            parameterWithName("q").description("검색어").optional(),
                            parameterWithName("orderId").description("주문 ID").optional(),
                            parameterWithName("page").description("페이지 번호").optional(),
                            parameterWithName("size").description("페이지 크기").optional(),
                            parameterWithName("sort").description("정렬 기준 (형식: 필드명,정렬방향)")
                                .optional())
                        .build()
                )
            )
        );
  }

  @Test
  @DisplayName("주문 수정 성공 테스트")
  void updateOrderSuccess() throws Exception {
    // Given
    UUID orderId = UUID.randomUUID();

    ReqOrderPutDtoApiV1 requestDto = ReqOrderPutDtoApiV1.builder()
        .order(ReqOrderPutDtoApiV1.Order.builder()
            .request("Sample Request Message")
            .build())
        .build();

    ResOrderPutDtoApiV1 responseDto = ResOrderPutDtoApiV1.builder()
        .order(ResOrderPutDtoApiV1.Order.builder()
            .id(orderId)
            .request("Sample Request Message")
            .updatedAt(ZonedDateTime.now()).build()
        ).build();

    // When & Then
    mockMvc.perform(RestDocumentationRequestBuilders.put("/v1/orders/{orderId}", orderId)
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
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Order V1")
                        .summary("주문 수정 성공")
                        .requestHeaders(
                            headerWithName("X-USER-ID").description("사용자 ID"),
                            headerWithName("X-USER-ROLE").description("사용자 권한"))
                        .build()
                )
            )
        );
  }

  @Test
  @DisplayName("주문 취소 성공 테스트")
  void cancelOrderSuccess() throws Exception {
    // Given
    UUID orderId = UUID.randomUUID();

    ReqOrderPostCancelDtoApiV1 requestDto = ReqOrderPostCancelDtoApiV1.builder()
        .order(ReqOrderPostCancelDtoApiV1.Order
            .builder()
            .cancelType(OrderCancelType.CUSTOMER_REQUEST)
            .cancelReason("Sample Cancel Reason")
            .build()
        )
        .build();

    ResOrderPostCancelDtoApiV1 responseDto = ResOrderPostCancelDtoApiV1.builder()
        .order(ResOrderPostCancelDtoApiV1.Order.builder()
            .id(orderId)
            .orderStatus(OrderStatus.CANCELED)
            .canceledAt(ZonedDateTime.now())
            .build())
        .build();

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
                resource(
                    ResourceSnippetParameters.builder()
                        .tag("Order V1").summary("주문 취소 성공")
                        .requestHeaders(
                            headerWithName("X-USER-ID").description("사용자 ID"),
                            headerWithName("X-USER-ROLE").description("사용자 권한"))
                        .build()
                )
            )
        );
  }
}
