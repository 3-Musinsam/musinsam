package com.musinsam.orderservice.presentation.controller;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.response.ApiSuccessCode;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.common.user.UserRoleType;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1;
import com.musinsam.orderservice.application.dto.request.ReqOrderPutDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderGetByIdDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderGetDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostCancelDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPostDtoApiV1;
import com.musinsam.orderservice.application.dto.response.ResOrderPutDtoApiV1;
import com.musinsam.orderservice.application.service.OrderServiceApiV1;
import com.musinsam.orderservice.domain.order.entity.OrderEntity;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 주문 Api V1.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderControllerApiV1 {

  private final OrderServiceApiV1 orderService;

  /**
   * 주문 생성.
   */
  @PostMapping
  @CustomPreAuthorize(userRoleType = {
      UserRoleType.ROLE_MASTER,
      UserRoleType.ROLE_COMPANY,
      UserRoleType.ROLE_USER
  })
  public ResponseEntity<ApiResponse<ResOrderPostDtoApiV1>> createOrder(
      @Valid @RequestBody ReqOrderPostDtoApiV1 requestDto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    ResOrderPostDtoApiV1 responseDto = orderService.createOrder(requestDto, currentUser.userId());

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiSuccessCode.OK.getCode(),
            "Order created successfully",
            responseDto
        )
    );
  }

  /**
   * 주문 상세 조회.
   */
  @GetMapping("/{orderId}")
  @CustomPreAuthorize(userRoleType = {
      UserRoleType.ROLE_MASTER,
      UserRoleType.ROLE_COMPANY,
      UserRoleType.ROLE_USER
  })
  public ResponseEntity<ApiResponse<ResOrderGetByIdDtoApiV1>> getOrderById(
      @PathVariable UUID orderId,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    ResOrderGetByIdDtoApiV1 responseDto = orderService.getOrder(orderId, currentUser.userId());

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiSuccessCode.OK.getCode(),
            "Order details retrieved successfully.",
            responseDto
        )
    );
  }

  /**
   * 주문 목록 조회.
   */
  @GetMapping
  @CustomPreAuthorize(userRoleType = {UserRoleType.ROLE_MASTER})
  public ResponseEntity<ApiResponse<ResOrderGetDtoApiV1>> getOrder(
      @QuerydslPredicate(root = OrderEntity.class) Predicate predicate,
      @PageableDefault
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      }) Pageable pageable,
      @RequestParam(required = false) String searchKeyword,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    ResOrderGetDtoApiV1 responseDto = orderService.getOrderList(
        predicate,
        pageable,
        searchKeyword,
        currentUser.userId()
    );

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiSuccessCode.OK.getCode(),
            "Orders retrieved successfully.",
            responseDto
        )
    );
  }

  /**
   * 주문 수정.
   */
  @PutMapping("/{orderId}")
  @CustomPreAuthorize(userRoleType = {UserRoleType.ROLE_MASTER,
      UserRoleType.ROLE_COMPANY,
      UserRoleType.ROLE_USER
  })
  public ResponseEntity<ApiResponse<ResOrderPutDtoApiV1>> updateOrder(
      @PathVariable UUID orderId,
      @Valid @RequestBody ReqOrderPutDtoApiV1 requestDto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    ResOrderPutDtoApiV1 responseDto = orderService.updateOrderStatus(orderId, requestDto,
        currentUser.userId());

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiSuccessCode.OK.getCode(),
            "Order updated successfully.",
            responseDto
        )
    );
  }

  /**
   * 주문 취소.
   */
  @PostMapping("/{orderId}/cancel")
  @CustomPreAuthorize(userRoleType = {UserRoleType.ROLE_MASTER,
      UserRoleType.ROLE_COMPANY,
      UserRoleType.ROLE_USER
  })
  public ResponseEntity<ApiResponse<ResOrderPostCancelDtoApiV1>> cancelOrder(
      @PathVariable UUID orderId,
      @Valid @RequestBody ReqOrderPostCancelDtoApiV1 requestDto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    ResOrderPostCancelDtoApiV1 responseDto = orderService.cancelOrder(orderId, requestDto,
        currentUser.userId());

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiSuccessCode.OK.getCode(),
            "Order canceled successfully.",
            responseDto
        )
    );
  }

  /**
   * 주문 삭제.
   */
  @DeleteMapping("/{orderId}")
  @CustomPreAuthorize(userRoleType = {UserRoleType.ROLE_MASTER})
  public ResponseEntity<ApiResponse<Void>> deleteOrder(
      @PathVariable UUID orderId,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {

    orderService.deleteOrder(orderId, currentUser.userId());

    return ResponseEntity.ok(
        ApiResponse.success("Order deleted successfully.")
    );
  }
}
