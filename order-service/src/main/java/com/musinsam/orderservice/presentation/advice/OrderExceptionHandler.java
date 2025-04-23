package com.musinsam.orderservice.presentation.advice;

import com.musinsam.common.exception.CommonErrorCode;
import com.musinsam.common.exception.CustomException;
import com.musinsam.common.exception.ErrorCode;
import com.musinsam.common.exception.ErrorResponse;
import com.musinsam.orderservice.domain.order.exception.OrderAccessDeniedException;
import com.musinsam.orderservice.domain.order.exception.OrderControlException;
import com.musinsam.orderservice.domain.order.exception.OrderCouponException;
import com.musinsam.orderservice.domain.order.exception.OrderException;
import com.musinsam.orderservice.domain.order.exception.OrderNotFoundException;
import com.musinsam.orderservice.domain.order.exception.OrderProductException;
import com.musinsam.orderservice.domain.order.exception.OrderStatusException;
import com.musinsam.orderservice.domain.order.exception.OrderStockRestoreException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "OrderExceptionHandler")
public class OrderExceptionHandler {

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


  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleOrderNotFoundException(
      OrderNotFoundException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, false);
  }

  @ExceptionHandler(OrderStatusException.class)
  public ResponseEntity<ErrorResponse> handleOrderStatusException(
      OrderStatusException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, false);
  }

  @ExceptionHandler(OrderAccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleOrderAccessDeniedException(
      OrderAccessDeniedException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, true);
  }

  @ExceptionHandler(OrderProductException.class)
  public ResponseEntity<ErrorResponse> handleOrderProductException(
      OrderProductException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, false);
  }

  @ExceptionHandler(OrderCouponException.class)
  public ResponseEntity<ErrorResponse> handleOrderCouponException(
      OrderCouponException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, false);
  }

  @ExceptionHandler(OrderControlException.class)
  public ResponseEntity<ErrorResponse> handleOrderControlException(
      OrderControlException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, false);
  }

  @ExceptionHandler(OrderStockRestoreException.class)
  public ResponseEntity<ErrorResponse> handleOrderStoreRestoreException(
      OrderStockRestoreException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, false);
  }

  @ExceptionHandler(OrderException.class)
  public ResponseEntity<ErrorResponse> handleOrderException(
      OrderException ex, HttpServletRequest request) {
    return createOrderBaseException(ex, request, true);
  }

  @ExceptionHandler(Exception.class)
  public ErrorResponse handleUnexpectedException(
      Exception ex, HttpServletRequest request) {

    printDangerousException(request, CommonErrorCode.INTERNAL_ERROR, ex);

    return new ErrorResponse(CommonErrorCode.INTERNAL_ERROR);
  }
}