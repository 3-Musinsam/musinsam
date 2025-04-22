package com.musinsam.shopservice.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqAlarmPostDtoApiV1 {

  @Valid
  @NotNull(message = "알림 정보를 입력해주세요.")
  private Alarm alarm;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Alarm {

    @NotBlank(message = "메시지를 입력해주세요.")
    private String message;

  }
}
