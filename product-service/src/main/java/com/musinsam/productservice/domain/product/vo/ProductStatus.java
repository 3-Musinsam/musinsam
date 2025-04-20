package com.musinsam.productservice.domain.product.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum ProductStatus {
  SHOW,
  SOLDOUT,
  HIDE;

  public static ProductStatus of(String status) {
    return valueOf(status);
  }

  @JsonCreator
  public static ProductStatus parsing(String inputValue) {
    return Stream.of(ProductStatus.values())
        .filter(productStatus -> productStatus.toString().equals(inputValue.toUpperCase()))
        .findFirst()
        .orElse(null);
  }
}
