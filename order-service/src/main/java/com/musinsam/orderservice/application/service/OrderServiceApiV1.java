package com.musinsam.orderservice.application.service;

import com.musinsam.orderservice.application.dto.request.ReqOrderPatchDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderGetByIdDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderGetDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPatchDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostDtoApiV1;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceApiV1 {

  public ResOrderPostDtoApiV1 createOrder(ReqOrderPostDtoApiV1 requestDto, Long userId) {
    return null;
  }

  public ResOrderGetByIdDtoApiV1 getOrder(UUID orderId, Long userId) {
    return null;
  }

  public ResOrderGetDtoApiV1 getOrderList(Pageable pageable, String searchKeyword, Long userId) {
    return null;
  }

  public ResOrderPatchDtoApiV1 updateOrder(UUID orderId, ReqOrderPatchDtoApiV1 requestDto,
      Long userId) {
    return null;
  }

  public ResOrderPostCancelDtoApiV1 cancelOrder(UUID orderId, ReqOrderPostCancelDtoApiV1 requestDto,
      Long userId) {
    return null;
  }

  public void deleteOrder(UUID orderId, Long userId) {
  }
}
