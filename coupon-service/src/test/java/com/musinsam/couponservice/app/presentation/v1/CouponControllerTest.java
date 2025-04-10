package com.musinsam.couponservice.app.presentation.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponClaimDtoApiV1;
import com.musinsam.couponservice.app.application.dto.v1.coupon.request.ReqCouponIssueDtoApiV1;
import com.musinsam.couponservice.app.presentation.v1.config.TestWebMvcConfig;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@Import(TestWebMvcConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CouponControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("[Coupon] 쿠폰 발급 성공")
  public void givenCouponPolicyIdWhenIssueThenSuccess() throws Exception {
    // given
    UUID couponPolicyId = UUID.randomUUID();

    ReqCouponIssueDtoApiV1 request = ReqCouponIssueDtoApiV1.builder()
        .couponPolicyId(couponPolicyId)
        .build();

    String json = objectMapper.writeValueAsString(request);

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/coupons")
                .contentType("application/json")
                .content(json)
                .header("X-USER-ID", 1)
                .header("X-USER-ROLE", "ROLE_COMPANY")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Coupon issued successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[Coupon] 쿠폰 지급 성공")
  public void givenCouponWhenClaimCouponTThenSuccess() throws Exception {
    // given
    UUID couponPolicyId = UUID.randomUUID();

    ReqCouponClaimDtoApiV1 request = ReqCouponClaimDtoApiV1.builder()
        .couponPolicyId(couponPolicyId)
        .build();

    String json = objectMapper.writeValueAsString(request);

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/coupons/claim")
                .contentType("application/json")
                .content(json)
                .header("X-USER-ID", "1")
                .header("X-USER-ROLE", "ROLE_USER")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Coupon claimed successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[Coupon] 쿠폰 사용 성공")
  public void givenCouponIdWhenUseCouponThenSuccess() throws Exception {
    // given
    UUID couponId = UUID.randomUUID();

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/coupons/{id}/use", couponId)
                .header("X-USER-ID", "1")
                .header("X-USER-ROLE", "ROLE_USER")
                .contentType("application/json")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Coupon claimed successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[Coupon] 모든 쿠폰 조회 성공")
  public void givenSearchParamsCouponWhenGetCouponsThenSuccess() throws Exception {
    // given
    String policyName = "봄맞이 할인쿠폰";

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/coupons")
                .header("X-USER-ID", "1")
                .header("X-USER-ROLE", "ROLE_COMPANY")
                .param("policyName", policyName)
                .param("page", "0")
                .param("size", "10")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Coupons retrieved successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[Coupon] 단 건 쿠폰 조회 성공")
  public void givenSearchParamsWhenGetCouponsThenSuccess() throws Exception {
    // given
    UUID couponId = UUID.randomUUID();

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/coupons/{id}", couponId)
                .header("X-USER-ID", "1")
                .header("X-USER-ROLE", "ROLE_COMPANY")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Coupon details retrieved successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[Coupon] 쿠폰 취소 성공")
  public void givenCouponWhenCancelThenSuccess() throws Exception {
    // given
    UUID couponId = UUID.randomUUID();

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/coupons/{id}/cancel", couponId)
                .header("X-USER-ID", "1")
                .header("X-USER-ROLE", "ROLE_USER")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Coupon usage cancelled successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[Coupon] 쿠폰 삭제 성공")
  public void givenCouponWhenDeleteThenSuccess() throws Exception {
    // given
    UUID couponId = UUID.randomUUID();

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.delete("/v1/coupons/{id}", couponId)
                .header("X-USER-ID", "1")
                .header("X-USER-ROLE", "ROLE_USER")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Coupon deleted successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

}

