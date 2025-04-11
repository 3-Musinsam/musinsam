package com.musinsam.productservice.application.dto.request;

import com.musinsam.productservice.domain.product.entity.ProductEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqProductPostDtoApiV1 {

  @Valid
  @NotNull(message = "상품 정보를 입력하세요.")
  private Product product;


  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Product {

    @NotBlank(message = "상품 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "상품 가격을 입력해주세요.")
    @PositiveOrZero(message = "상품 가격은 0원 이상이어야 합니다.")
    private BigDecimal price;

    @NotNull(message = "상품 재고를 입력해주세요.")
    @PositiveOrZero(message = "상품 재고는 0개 이상이어야 합니다.")
    private Integer stock;

    @NotNull(message = "상점 id값을 입력해주세요.")
    private UUID shopId;


    public ProductEntity toEntity() {
      return ProductEntity.builder()
          .build();
    }
  }

}
