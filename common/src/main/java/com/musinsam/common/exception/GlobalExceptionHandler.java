package com.musinsam.common.exception;

import com.musinsam.common.exception.ErrorResponse.FieldError;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
    ErrorCode errorCode = ex.getErrorCode();

    log.warn("CustomException occurred: [{}] {}", errorCode.getCode(), errorCode.getMessage(), ex);

    ErrorResponse response = new ErrorResponse(errorCode);
    return new ResponseEntity<>(response, errorCode.getHttpStatus());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
    List<FieldError> fieldErrors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> new ErrorResponse.FieldError(
            error.getField(),
            String.valueOf(error.getRejectedValue()),
            error.getDefaultMessage()))
        .collect(Collectors.toList());

    log.warn("Validation failed: {} field error(s) - {}", fieldErrors.size(), fieldErrors);

    ErrorResponse response = new ErrorResponse(CommonErrorCode.INVALID_INPUT, fieldErrors);
    return new ResponseEntity<>(response, CommonErrorCode.INVALID_INPUT.getHttpStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    log.error("Unhandled exception occurred: {}", ex.getMessage(), ex);

    ErrorResponse response = new ErrorResponse(CommonErrorCode.INTERNAL_ERROR);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}