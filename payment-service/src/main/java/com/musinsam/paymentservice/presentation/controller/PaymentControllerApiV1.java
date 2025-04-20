package com.musinsam.paymentservice.presentation.controller;

import static com.musinsam.paymentservice.domain.payment.vo.PaymentResponseCode.PAYMENT_APPROVE_SUCCESS;
import static com.musinsam.paymentservice.domain.payment.vo.PaymentResponseCode.PAYMENT_CANCEL_SUCCESS;
import static com.musinsam.paymentservice.domain.payment.vo.PaymentResponseCode.PAYMENT_GET_SUCCESS;
import static com.musinsam.paymentservice.domain.payment.vo.PaymentResponseCode.PAYMENT_INIT_SUCCESS;
import static com.musinsam.paymentservice.domain.payment.vo.PaymentResponseCode.PAYMENT_LIST_GET_SUCCESS;

import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
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
            PAYMENT_INIT_SUCCESS.getCode(),
            PAYMENT_INIT_SUCCESS.getMessage(),
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
            PAYMENT_APPROVE_SUCCESS.getCode(),
            PAYMENT_APPROVE_SUCCESS.getMessage(),
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
            PAYMENT_GET_SUCCESS.getCode(),
            PAYMENT_GET_SUCCESS.getMessage(),
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
            PAYMENT_LIST_GET_SUCCESS.getCode(),
            PAYMENT_LIST_GET_SUCCESS.getMessage(),
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
            PAYMENT_CANCEL_SUCCESS.getCode(),
            PAYMENT_CANCEL_SUCCESS.getMessage(),
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
