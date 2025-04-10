package com.musinsam.paymentservice.application.service;

import com.musinsam.paymentservice.application.dto.request.ReqPaymentPatchStatusDtoApiV1;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostApproveDtoApiV1;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostCancelDtoApiV1;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostDtoApiV1;
import com.musinsam.paymentservice.application.dto.request.ReqPaymentPostInitDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentGetByIdDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentGetDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPatchStatusDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostApproveDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostCancelDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostDtoApiV1;
import com.musinsam.paymentservice.application.dto.response.ResPaymentPostInitDtoApiV1;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceApiV1 {

  public ResPaymentPostInitDtoApiV1 initPayment(ReqPaymentPostInitDtoApiV1 requestDto,
      Long userId) {
    return null;
  }

  public ResPaymentPostDtoApiV1 createPayment(ReqPaymentPostDtoApiV1 requestDto,
      Long userId) {
    return null;
  }

  public ResPaymentPostApproveDtoApiV1 approvePayment(
      @Valid ReqPaymentPostApproveDtoApiV1 requestDto, Long userId) {
    return null;
  }

  public ResPaymentGetByIdDtoApiV1 getPayment(UUID paymentId, Long userId) {
    return null;
  }

  public ResPaymentGetDtoApiV1 getPaymentList(Pageable pageable, Long userId) {
    return null;
  }

  public ResPaymentPostCancelDtoApiV1 cancelPayment(UUID paymentId,
      ReqPaymentPostCancelDtoApiV1 requestDto, Long userId) {
    return null;
  }

  public ResPaymentPatchStatusDtoApiV1 updatePaymentStatus(UUID paymentId,
      ReqPaymentPatchStatusDtoApiV1 requestDto, Long userId) {
    return null;
  }
}
