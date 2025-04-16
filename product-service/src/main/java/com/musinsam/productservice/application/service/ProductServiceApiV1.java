package com.musinsam.productservice.application.service;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.productservice.application.dto.request.ReqProductPostDtoApiV1;
import com.musinsam.productservice.application.dto.request.ReqProductPutByProductIdDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetByProductIdDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetStockDtoApiV1;
import jakarta.validation.Valid;
import java.util.UUID;

public interface ProductServiceApiV1 {

  void createProduct(CurrentUserDtoApiV1 currentUser, @Valid ReqProductPostDtoApiV1 dto);

  ResProductGetByProductIdDtoApiV1 getById(UUID productId);

  ResProductGetDtoApiV1 getProductList(int page, int size);

  void updateProduct(CurrentUserDtoApiV1 currentUser, UUID productId,
      @Valid ReqProductPutByProductIdDtoApiV1 dto);

  void deleteProduct(CurrentUserDtoApiV1 currentUser, UUID productId);

  ResProductGetStockDtoApiV1 getProductStock(CurrentUserDtoApiV1 currentUser, UUID productId);
}
