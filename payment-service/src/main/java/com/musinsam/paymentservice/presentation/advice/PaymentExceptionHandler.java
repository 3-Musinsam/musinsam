package com.musinsam.paymentservice.presentation.advice;

import com.musinsam.common.exception.CommonErrorCode;
import com.musinsam.common.exception.CustomException;
import com.musinsam.common.exception.ErrorCode;
import com.musinsam.common.exception.ErrorResponse;
import com.musinsam.paymentservice.domain.payment.exception.PaymentAccessDeniedException;
import com.musinsam.paymentservice.domain.payment.exception.PaymentControlException;
import com.musinsam.paymentservice.domain.payment.exception.PaymentNotFoundException;
import com.musinsam.paymentservice.domain.payment.exception.PaymentStatusException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "PaymentExceptionHandler")
public class PaymentExceptionHandler {

  private void printException(HttpServletRequest request, ErrorCode errorCode) {
    log.warn("Code:[{}] : {}, {}", errorCode.getCode(), errorCode.getMessage(),
        request.getRequestURI());
  }

  private void printDangerousException(HttpServletRequest request, ErrorCode errorCode,
      Exception ex) {
    log.error("Code:[{}] {} - Method: {}, Client IP: {}",
        errorCode.getCode(),
        request.getRequestURI(),
        request.getMethod(),
        request.getRemoteAddr(),
        ex
    );
  }

  private ResponseEntity<ErrorResponse> createOrderBaseException(
      CustomException ex,
      HttpServletRequest request,
      boolean isDangerous
  ) {

    ErrorCode errorCode = ex.getErrorCode();

    if (isDangerous) {
      printDangerousException(request, errorCode, ex);
    } else {
      printException(request, errorCode);
    }

    ErrorResponse errorResponse = new ErrorResponse(errorCode);
    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(errorResponse);
  }


  @ExceptionHandler(PaymentNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlePaymentNotFoundException(
      PaymentNotFoundException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, false);
  }

  @ExceptionHandler(PaymentStatusException.class)
  public ResponseEntity<ErrorResponse> handlePaymentStatusException(
      PaymentStatusException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, false);
  }

  @ExceptionHandler(PaymentAccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handlePaymentAccessDeniedException(
      PaymentAccessDeniedException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, false);
  }

  @ExceptionHandler(PaymentControlException.class)
  public ResponseEntity<ErrorResponse> handlePaymentControlException(
      PaymentControlException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, false);
  }

  @ExceptionHandler(Exception.class)
  public ErrorResponse handleUnexpectedException(
      Exception ex, HttpServletRequest request) {

    printDangerousException(request, CommonErrorCode.INTERNAL_ERROR, ex);

    return new ErrorResponse(CommonErrorCode.INTERNAL_ERROR);
  }
}
