package com.musinsam.productservice.application.service.impl;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.productservice.application.dto.request.ReqProductPostDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetByProductIdDtoApiV1;
import com.musinsam.productservice.application.service.ProductServiceApiV1;
import com.musinsam.productservice.domain.product.entity.ProductEntity;
import com.musinsam.productservice.domain.product.entity.ProductImageEntity;
import com.musinsam.productservice.domain.product.repository.ProductImageRepository;
import com.musinsam.productservice.domain.product.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImplApiV1 implements ProductServiceApiV1 {

  private final ProductRepository productRepository;
  private final ProductImageRepository productImageRepository;

  @Override
  @Transactional
  public void createProduct(CurrentUserDtoApiV1 currentUser, ReqProductPostDtoApiV1 dto) {

    ProductEntity product = dto.getProduct().toEntity();
    productRepository.save(product);

    // TODO: S3 이미지 업로드 구현
//    for (MultipartFile image : images) {
//    }
  }

  @Override
  @Transactional
  public ResProductGetByProductIdDtoApiV1 getById(UUID productId) {

    ProductEntity product = productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new RuntimeException());

    List<ProductImageEntity> productImages = productImageRepository.findByProductIdAndDeletedAtIsNull(
        productId);

    // TODO: shop feign client 호출
    String shopName = "상점이름1";

    ResProductGetByProductIdDtoApiV1 resDto = ResProductGetByProductIdDtoApiV1.of(product,
        productImages, shopName);

    return resDto;
  }

}
