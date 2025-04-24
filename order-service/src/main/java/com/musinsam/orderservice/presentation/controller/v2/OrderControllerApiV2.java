package com.musinsam.orderservice.presentation.controller.v2;

import static com.musinsam.orderservice.domain.order.vo.OrderResponseCode.ORDER_CANCEL_SUCCESS;
import static com.musinsam.orderservice.domain.order.vo.OrderResponseCode.ORDER_POST_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.common.user.UserRoleType;
import com.musinsam.orderservice.application.dto.request.v2.ReqOrderPostCancelDtoApiV2;
import com.musinsam.orderservice.application.dto.request.v2.ReqOrderPostDtoApiV2;
import com.musinsam.orderservice.application.dto.response.v2.ResOrderPostCancelDtoApiV2;
import com.musinsam.orderservice.application.dto.response.v2.ResOrderPostDtoApiV2;
import com.musinsam.orderservice.application.service.v2.OrderServiceApiV2;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/orders")
public class OrderControllerApiV2 {

  private final OrderServiceApiV2 orderServiceApiV2;

  @PostMapping
  @CustomPreAuthorize(userRoleType = {
      UserRoleType.ROLE_MASTER,
      UserRoleType.ROLE_COMPANY,
      UserRoleType.ROLE_USER
  })
  public ResponseEntity<ApiResponse<ResOrderPostDtoApiV2>> createOrder(
      @Valid @RequestBody ReqOrderPostDtoApiV2 requestDto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResOrderPostDtoApiV2 responseDto = orderServiceApiV2.createOrder(requestDto,
        currentUser.userId());

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ORDER_POST_SUCCESS.getCode(),
            ORDER_POST_SUCCESS.getMessage(),
            responseDto
        )
    );
  }

  @PostMapping("/{orderId}/cancel")
  @CustomPreAuthorize(userRoleType = {
      UserRoleType.ROLE_MASTER,
      UserRoleType.ROLE_COMPANY,
      UserRoleType.ROLE_USER
  })
  public ResponseEntity<ApiResponse<ResOrderPostCancelDtoApiV2>> cancelOrder(
      @PathVariable UUID orderId,
      @Valid @RequestBody ReqOrderPostCancelDtoApiV2 requestDto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    ResOrderPostCancelDtoApiV2 responseDto = orderServiceApiV2.cancelOrder(orderId, requestDto,
        currentUser.userId());

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ORDER_CANCEL_SUCCESS.getCode(),
            ORDER_CANCEL_SUCCESS.getMessage(),
            responseDto
        )
    );
  }
}
