package com.musinsam.orderservice.application.dto.request;

import jakarta.validation.Valid;
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

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Cancel {

    private String reason;
  }

  @Valid
  private Cancel cancel;
}
