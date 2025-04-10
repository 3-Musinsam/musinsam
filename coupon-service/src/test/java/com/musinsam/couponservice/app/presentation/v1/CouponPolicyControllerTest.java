package com.musinsam.couponservice.app.presentation.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsam.couponservice.app.application.dto.v1.couponPolicy.request.ReqCouponPolicyIssueDtoApiV1;
import com.musinsam.couponservice.app.doamin.vo.DiscountType;
import com.musinsam.couponservice.app.presentation.v1.config.TestWebMvcConfig;
import java.time.ZonedDateTime;
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
class CouponPolicyControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("[CouponPolicy] 쿠폰정책 발급 성공")
  public void givenCouponPolicyWhenIssueThenSuccess() throws Exception {
    // given
    ReqCouponPolicyIssueDtoApiV1.CouponPolicy couponPolicy = ReqCouponPolicyIssueDtoApiV1.CouponPolicy.builder()
        .name("여름맞이 할인 쿠폰")
        .description("전 상품 15% 할인")
        .discountType(DiscountType.PERCENTAGE)
        .discountValue(15)
        .minimumOrderAmount(20000)
        .maximumDiscountAmount(30000)
        .totalQuantity(500)
        .startedAt(ZonedDateTime.now().minusDays(1))
        .endedAt(ZonedDateTime.now().plusDays(10))
        .companyId(UUID.randomUUID())
        .build();

    ReqCouponPolicyIssueDtoApiV1 request = ReqCouponPolicyIssueDtoApiV1.builder()
        .couponPolicy(couponPolicy)
        .build();

    String json = objectMapper.writeValueAsString(request);

    // when + then
    mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/coupon-policies")
                .contentType("application/json")
                .content(json)
                .header("X-USER-ID", "1")
                .header("X-USER-ROLE", "ROLE_COMPANY")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Coupon issued successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[CouponPolicy] 단 건 쿠폰정책 조회")
  public void givenCouponPolicyIdWhenGetThenSuccess() throws Exception {
    // given
    UUID couponPolicyId = UUID.randomUUID();

    // when + then
    mockMvc.perform(MockMvcRequestBuilders.get("/v1/coupon-policies/{id}", couponPolicyId)
            .header("X-USER-ID", "1")
            .header("X-USER-ROLE", "ROLE_USER")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Coupon policy retrieved successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }


  @Test
  @DisplayName("[CouponPolicy] 쿠폰 정책 조회")
  public void givenParamsWhenGetCouponPoliciesThenSuccess() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/v1/coupon-policies")
            .header("X-USER-ID", "1")
            .header("X-USER-ROLE", "ROLE_COMPANY")
            .param("policyName", "신규회원")
            .param("startedFrom", ZonedDateTime.now().minusDays(1).toString())
            .param("startedTo", ZonedDateTime.now().plusDays(10).toString())
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Coupon policies retrieved successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  @DisplayName("[CouponPolicy] 쿠폰정책 삭제")
  public void givenCouponIdWhenDeleteThenSuccess() throws Exception {
    // given
    UUID couponPolicyId = UUID.randomUUID();

    // when + then
    mockMvc.perform(MockMvcRequestBuilders.delete("/v1/coupon-policies/{id}", couponPolicyId)
            .header("X-USER-ID", "1")
            .header("X-USER-ROLE", "ROLE_COMPANY")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Coupon policy deleted successfully."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
        .andDo(MockMvcResultHandlers.print());
  }
}
