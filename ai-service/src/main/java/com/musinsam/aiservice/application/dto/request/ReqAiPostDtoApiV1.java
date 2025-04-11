package com.musinsam.aiservice.application.dto.request;

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
public class ReqAiPostDtoApiV1 {

  @Valid
  @NotNull(message = "Ai 정보를 입력해주세요.")
  private Ai ai;

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Ai {

    @NotBlank(message = "요청 메시지를 입력해주세요.")
    private String prompt;

  }
}
