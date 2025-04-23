package com.musinsam.paymentservice.infrastructure.client;

import com.musinsam.common.config.FeignConfig;
import com.musinsam.paymentservice.infrastructure.client.dto.request.ReqOrderClientUpdateOrderStatusDto;
import com.musinsam.paymentservice.infrastructure.client.dto.response.ResOrderClientGetByOrderIdDto;
import com.musinsam.paymentservice.infrastructure.client.dto.response.ResOrderClientUpdateOrderStatusDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "order-service",
    configuration = FeignConfig.class
)
public interface OrderFeignClient {

  @GetMapping("/internal/v1/orders/{orderId}")
  ResOrderClientGetByOrderIdDto getOrderById(@PathVariable("orderId") UUID orderId);

  @PostMapping("/internal/v1/orders/{orderId}")
  ResOrderClientUpdateOrderStatusDto updateOrderStatus(
      @PathVariable("orderId") UUID orderId,
      @RequestBody ReqOrderClientUpdateOrderStatusDto reqDto
  );
}
