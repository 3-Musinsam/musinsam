package com.musinsam.productservice.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqProductUpdateDtoApiV1 {

  @Valid
  @NotNull(message = "수정할 상품 정보를 입력해주세요.")
  private UpdatedProduct updatedProduct;


  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UpdatedProduct {

    @NotBlank(message = "상품 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "상품 가격을 입력해주세요.")
    @PositiveOrZero(message = "상품 가격은 0원 이상이어야 합니다.")
    private BigDecimal price;

    private List<UUID> unchangedImages;

  }

}
