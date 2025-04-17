package com.musinsam.productservice.infrastructure.image;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum S3Folder {
  PRODUCT("product");

  private final String folderName;
}
