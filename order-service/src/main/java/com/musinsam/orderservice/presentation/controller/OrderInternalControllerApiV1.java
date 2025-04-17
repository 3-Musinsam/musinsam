package com.musinsam.orderservice.presentation.controller;

import com.musinsam.orderservice.application.dto.client.OrderClientDto;
import com.musinsam.orderservice.application.service.OrderServiceApiV1;
import com.musinsam.orderservice.infrastructure.client.dto.request.ReqOrderClientUpdateOrderStatusDto;
import com.musinsam.orderservice.infrastructure.client.dto.response.ResOrderClientUpdateOrderStatusDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/orders")
public class OrderInternalControllerApiV1 {

  private final OrderServiceApiV1 orderService;

  @GetMapping("/{orderId}")
  public OrderClientDto getOrderClientById(@PathVariable UUID orderId) {
    return orderService.getOrderClientDtoById(orderId);
  }

  @PostMapping("/{orderId}")
  public ResOrderClientUpdateOrderStatusDto updateOrderClientStatus(
      @PathVariable UUID orderId,
      @RequestBody ReqOrderClientUpdateOrderStatusDto requestDto
  ) {
    return orderService.updateOrderStatus(orderId, requestDto);
  }
}
