package com.musinsam.paymentservice.application.service;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostApproveDtoApiV1;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostCancelDtoApiV1;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostInitDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentGetByIdDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentGetDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostApproveDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostCancelDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostInitDtoApiV1;
import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
import com.musinsam.paymentservice.domain.payment.vo.PaymentMethod;
import com.musinsam.paymentservice.domain.payment.vo.PaymentStatus;
import com.musinsam.paymentservice.domain.repository.PaymentRepository;
import com.musinsam.paymentservice.global.response.PaymentErrorCode;
import com.musinsam.paymentservice.infrastructure.client.OrderFeignClient;
import com.musinsam.paymentservice.infrastructure.client.TossPaymentFeignClient;
import com.musinsam.paymentservice.infrastructure.client.dto.request.ReqOrderClientUpdateOrderStatusDto;
import com.musinsam.paymentservice.infrastructure.client.dto.request.ReqTossPaymentApproveDto;
import com.musinsam.paymentservice.infrastructure.client.dto.response.ResOrderClientGetByOrderIdDto;
import com.musinsam.paymentservice.infrastructure.client.dto.response.ResTossPaymentApproveDto;
import com.musinsam.paymentservice.infrastructure.config.PaymentConfig;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceApiV1 {

  private final PaymentRepository paymentRepository;
  private final OrderFeignClient orderFeignClient;
  private final TossPaymentFeignClient tossPaymentFeignClient;
  private final PaymentConfig paymentConfig;

  @Transactional(readOnly = true)
  public ResPaymentPostInitDtoApiV1 initPayment(ReqPaymentPostInitDtoApiV1 requestDto,
      Long userId
  ) {

    ResOrderClientGetByOrderIdDto responseOrderClientDto = orderFeignClient.getOrderById(
        requestDto.getOrderId());

    if (!responseOrderClientDto.getOrder().getUserId().equals(userId)) {
      throw CustomException.from(PaymentErrorCode.PAYMENT_UNAUTHORIZED);
    }

    if (!responseOrderClientDto.getOrder().getOrderStatus().equals("PENDING")) {
      throw CustomException.from(PaymentErrorCode.PAYMENT_INVALID_ORDER_STATUS);
    }

    return ResPaymentPostInitDtoApiV1.of(responseOrderClientDto, paymentConfig);
  }

  @Transactional
  public ResPaymentPostApproveDtoApiV1 approvePayment(
      ReqPaymentPostApproveDtoApiV1 requestDto,
      Long userId
  ) {

    ResOrderClientGetByOrderIdDto responseOrderClientDto = orderFeignClient.getOrderById(
        UUID.fromString(requestDto.getPaymentApproval().getOrderId()));

    if (!responseOrderClientDto.getOrder().getUserId().equals(userId)) {
      throw CustomException.from(PaymentErrorCode.PAYMENT_UNAUTHORIZED);
    }

    if (responseOrderClientDto.getOrder().getFinalAmount()
        .compareTo(requestDto.getPaymentApproval().getAmount()) != 0) {
      throw CustomException.from(PaymentErrorCode.PAYMENT_AMOUNT_MISMATCH);
    }

    ReqTossPaymentApproveDto tossRequest = ReqTossPaymentApproveDto.of(requestDto);
    ResTossPaymentApproveDto tossResponse = tossPaymentFeignClient.confirmPayment(
        paymentConfig.getAuthorizationHeader(), tossRequest
    );

    boolean isPaymentFailed = Optional.ofNullable(tossResponse.getFailure()).isPresent();

    if (isPaymentFailed) {
      // 결제 실패
      UUID failedOrderId = UUID.fromString(requestDto.getPaymentApproval().getOrderId());

      PaymentEntity failurePayment = PaymentEntity.builder()
          .userId(userId)
          .orderId(failedOrderId)
          .providerOrderId(requestDto.getPaymentApproval().getOrderId())
          .methodType(PaymentMethod.valueOf(tossResponse.getMethod()))
          .paymentKey(tossResponse.getPaymentKey())
          .amount(tossResponse.getTotalAmount())
          .status(tossResponse.getStatus())
          .build();

      orderFeignClient.updateOrderStatus(
          failedOrderId,
          ReqOrderClientUpdateOrderStatusDto.failureWithTossResponse(failedOrderId,
              tossResponse.getFailure())
      );

      return ResPaymentPostApproveDtoApiV1.of(failurePayment);

    } else {
      // 결제 성공
      PaymentEntity succeedPayment = requestDto.getPaymentApproval().toPaymentEntity(
          userId,
          PaymentMethod.descriptionOf(tossResponse.getMethod()),
          tossResponse.getStatus()
      );

      paymentRepository.save(succeedPayment);

      UUID succeedOrderId = UUID.fromString(tossResponse.getOrderId());
      orderFeignClient.updateOrderStatus(
          succeedOrderId,
          ReqOrderClientUpdateOrderStatusDto.successOf(succeedOrderId)
      );

      return ResPaymentPostApproveDtoApiV1.of(succeedPayment);
    }
  }

  @Transactional(readOnly = true)
  public ResPaymentGetByIdDtoApiV1 getPayment(UUID paymentId, Long userId) {
    PaymentEntity paymentEntity = paymentRepository.findById(paymentId)
        .orElseThrow(() -> CustomException.from(PaymentErrorCode.PAYMENT_NOT_FOUND));

    return ResPaymentGetByIdDtoApiV1.of(paymentEntity);
  }

  @Transactional(readOnly = true)
  public ResPaymentGetDtoApiV1 getPaymentList(
      Predicate predicate,
      Pageable pageable,
      CurrentUserDtoApiV1 currentUser
  ) {
    Page<PaymentEntity> paymentEntities = paymentRepository.findAll(predicate, pageable);
    return ResPaymentGetDtoApiV1.of(paymentEntities);
  }

  @Transactional
  public ResPaymentPostCancelDtoApiV1 cancelPayment(UUID paymentId,
      ReqPaymentPostCancelDtoApiV1 requestDto, Long userId) {

    PaymentEntity paymentEntity = paymentRepository.findById(paymentId)
        .orElseThrow(() -> CustomException.from(PaymentErrorCode.PAYMENT_NOT_FOUND));

    if (!paymentEntity.getUserId().equals(userId)) {
      throw CustomException.from(PaymentErrorCode.PAYMENT_UNAUTHORIZED);
    }

    if (PaymentStatus.CANCELED.equals(paymentEntity.getStatus())) {
      throw CustomException.from(PaymentErrorCode.PAYMENT_INVALID_STATUS);
    }

    tossPaymentFeignClient.cancelPayment(
        requestDto.getPaymentCancel().getCancelReason(),
        requestDto.getPaymentCancel().getCancelAmount()
    );

    paymentRepository.save(paymentEntity);

    orderFeignClient.updateOrderStatus(
        paymentEntity.getOrderId(),
        ReqOrderClientUpdateOrderStatusDto.cancelOf(paymentEntity)
    );

    return ResPaymentPostCancelDtoApiV1.of(paymentEntity);
  }
}
