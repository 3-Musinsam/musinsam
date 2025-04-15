package com.musinsam.productservice.application.service;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.productservice.application.dto.request.ReqProductPostDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetByProductIdDtoApiV1;
import jakarta.validation.Valid;
import java.util.UUID;

public interface ProductServiceApiV1 {

  void createProduct(CurrentUserDtoApiV1 currentUser, @Valid ReqProductPostDtoApiV1 dto);

  ResProductGetByProductIdDtoApiV1 getById(UUID productId);

}
