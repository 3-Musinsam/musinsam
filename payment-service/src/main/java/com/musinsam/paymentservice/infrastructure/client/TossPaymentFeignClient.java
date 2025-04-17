package com.musinsam.paymentservice.infrastructure.client;

import com.musinsam.common.config.FeignConfig;
import com.musinsam.paymentservice.infrastructure.client.dto.request.ReqTossPaymentApproveDto;
import com.musinsam.paymentservice.infrastructure.client.dto.response.ResTossPaymentApproveDto;
import com.musinsam.paymentservice.infrastructure.client.dto.response.ResTossPaymentCancelDto;
import java.math.BigDecimal;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "toss-payment",
    configuration = FeignConfig.class,
    url = "${toss.payments.api-url}"
)
public interface TossPaymentFeignClient {

  /**
   * amount 필수 · number, orderId 필수 · string, paymentKey 필수 · string
   **/
  @PostMapping("/v1/payments/confirm")
  ResTossPaymentApproveDto confirmPayment(
      @RequestHeader("Authorization") String authorization,
      @RequestBody ReqTossPaymentApproveDto requestBody
  );

  @GetMapping("/v1/payments/{paymentKey}")
  Map<String, Object> getByPaymentKey(
      @RequestHeader("Authorization") String authorization,
      @PathVariable("paymentKey") String paymentKey
  );

  @GetMapping("/v1/payments/orders/{orderId}")
  Map<String, Object> getByOrderId(
      @RequestHeader("Authorization") String authorization,
      @PathVariable("orderId") String orderId
  );

  /**
   * cancelReason 필수 · string
   **/
  @PostMapping("/v1/payments/{paymentKey}/cancel")
  ResTossPaymentCancelDto cancelPayment(
      @RequestHeader("Authorization") String authorization,
      @RequestBody BigDecimal requestBody
  );
}
