package com.musinsam.paymentservice.presentation.controller;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.response.ApiSuccessCode;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.common.user.UserRoleType;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostApproveDtoApiV1;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostCancelDtoApiV1;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostInitDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentGetByIdDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentGetDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostApproveDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostCancelDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostInitDtoApiV1;
import com.musinsam.paymentservice.application.service.PaymentServiceApiV1;
import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 결제 Api V1.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
public class PaymentControllerApiV1 {

  private final PaymentServiceApiV1 paymentService;

  /**
   * 결제 서비스 초기 요청.
   */
  @PostMapping("/init")
  @CustomPreAuthorize(userRoleType = {
      UserRoleType.ROLE_MASTER,
      UserRoleType.ROLE_COMPANY,
      UserRoleType.ROLE_USER
  })
  public ResponseEntity<ApiResponse<ResPaymentPostInitDtoApiV1>> initPayment(
      @Valid @RequestBody ReqPaymentPostInitDtoApiV1 requestDto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResPaymentPostInitDtoApiV1 responseDto = paymentService.initPayment(requestDto,
        currentUser.userId());

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiSuccessCode.OK.getCode(),
            "Payment initialization successful",
            responseDto
        )
    );
  }

  /**
   * 결제 승인.
   */
  @PostMapping("/approve")
  @CustomPreAuthorize(userRoleType = {
      UserRoleType.ROLE_MASTER,
      UserRoleType.ROLE_COMPANY,
      UserRoleType.ROLE_USER
  })
  public ResponseEntity<ApiResponse<ResPaymentPostApproveDtoApiV1>> approvePayment(
      @Valid @RequestBody ReqPaymentPostApproveDtoApiV1 requestDto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResPaymentPostApproveDtoApiV1 responseDto = paymentService.approvePayment(requestDto,
        currentUser.userId());

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiSuccessCode.OK.getCode(),
            "Payment approved successfully",
            responseDto
        )
    );
  }

  /**
   * 결제 상세 조회.
   */
  @GetMapping("/{paymentId}")
  @CustomPreAuthorize(userRoleType = {
      UserRoleType.ROLE_MASTER,
      UserRoleType.ROLE_COMPANY,
      UserRoleType.ROLE_USER
  })
  public ResponseEntity<ApiResponse<ResPaymentGetByIdDtoApiV1>> getPaymentById(
      @PathVariable UUID paymentId,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResPaymentGetByIdDtoApiV1 responseDto = paymentService.getPayment(paymentId,
        currentUser.userId());

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiSuccessCode.OK.getCode(),
            "Payment details retrieved successfully",
            responseDto
        )
    );
  }

  /**
   * 결제 목록 조회.
   */
  @GetMapping
  @CustomPreAuthorize(userRoleType = {UserRoleType.ROLE_MASTER})
  public ResponseEntity<ApiResponse<ResPaymentGetDtoApiV1>> getPayments(
      @QuerydslPredicate(root = PaymentEntity.class) Predicate predicate,
      @PageableDefault
      @SortDefault.SortDefaults({
          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
      }) Pageable pageable,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResPaymentGetDtoApiV1 responseDto = paymentService.getPaymentList(
        predicate, pageable, currentUser);

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiSuccessCode.OK.getCode(),
            "Payments retrieved successfully",
            responseDto
        )
    );
  }

  /**
   * 결제 취소.
   */
  @PostMapping("/{paymentId}/cancel")
  @CustomPreAuthorize(userRoleType = {
      UserRoleType.ROLE_MASTER,
      UserRoleType.ROLE_COMPANY,
      UserRoleType.ROLE_USER
  })
  public ResponseEntity<ApiResponse<ResPaymentPostCancelDtoApiV1>> cancelPayment(
      @PathVariable UUID paymentId,
      @Valid @RequestBody ReqPaymentPostCancelDtoApiV1 requestDto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    ResPaymentPostCancelDtoApiV1 responseDto = paymentService.cancelPayment(paymentId, requestDto,
        currentUser.userId());

    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiSuccessCode.OK.getCode(),
            "Payment cancelled successfully",
            responseDto
        )
    );
  }

  /**
   * *
   *
   * @param payload
   */
//  @PostMapping("/webhook")
//  public ResponseEntity<Void> receiveWebhook(@RequestBody TossWebhookPayload payload) {
//    paymentService.handleWebhook(payload);
//    return ResponseEntity.ok().build();
//  }
}
