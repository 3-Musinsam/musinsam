package com.musinsam.orderservice.application.service;

import com.musinsam.common.exception.CommonErrorCode;
import com.musinsam.common.exception.CustomException;
import com.musinsam.orderservice.app.global.response.OrderErrorCode;
import com.musinsam.orderservice.application.dto.request.ReqOrderPatchDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderGetByIdDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderGetDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPatchDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostDtoApiV1;
import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.musinsam.orderservice.domain.order.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceApiV1 {

  private final OrderRepository orderRepository;

  @Transactional
  public ResOrderPostDtoApiV1 createOrder(ReqOrderPostDtoApiV1 requestDto, Long userId) {

    OrderEntity orderEntity = requestDto.getOrder().toEntityWith(userId);

    validateOrderOwner(orderEntity, userId);

    // TODO: 상품 서비스에 재고 확인 요청 및 차감
    //  boolean stockAvailable = validateAndReserveStock(requestDto);
    boolean stockAvailable = true;

    if (!stockAvailable) {
      throw CustomException.from(OrderErrorCode.ORDER_PRODUCT_NOT_AVAILABLE);
    }

    OrderEntity savedOrder = orderRepository.save(orderEntity);

    // TODO: 주문 이벤트 발행
    //  publishOrderCreatedEvent(savedOrder);

    return ResOrderPostDtoApiV1.of(savedOrder);
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

  private void validateOrderOwner(OrderEntity orderEntity, Long userId) {
    if (!orderEntity.getUserId().equals(userId)) {
      throw CustomException.from(CommonErrorCode.FORBIDDEN);
    }
  }
}
