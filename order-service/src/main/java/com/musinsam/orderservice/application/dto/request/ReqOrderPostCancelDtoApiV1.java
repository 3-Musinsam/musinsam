package com.musinsam.orderservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 취소 요청 DTO.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqOrderPostCancelDtoApiV1 {

  private String cancelReason;
}
