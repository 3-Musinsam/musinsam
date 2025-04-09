package com.musinsam.aiservice.presentation.controller;

import com.musinsam.aiservice.application.dto.request.ReqAiCouponPostDtoApiV1;
import com.musinsam.aiservice.application.dto.request.ReqAiOrderPostDtoApiV1;
import com.musinsam.aiservice.application.service.AiServiceApiV1;
import com.musinsam.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ais")
public class AiController {

  public AiServiceApiV1 aiServiceApiV1;

  @PostMapping("/order")
  public ResponseEntity<ApiResponse<String>> postByOrder(
      @RequestBody ReqAiOrderPostDtoApiV1 dto
  ) {
    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiResponse.success().getCode(),
            "The order ai message was successfully sent.",
            aiServiceApiV1.postByOrder(dto)
        )
    );
  }

  @PostMapping("/coupon")
  public ResponseEntity<ApiResponse<String>> postByCoupon(
      @RequestBody ReqAiCouponPostDtoApiV1 dto
  ) {
    return ResponseEntity.ok().body(
        ApiResponse.success(
            ApiResponse.success().getCode(),
            "The coupon ai message was successfully sent.",
            aiServiceApiV1.postByCoupon(dto)
        )
    );
  }
}
