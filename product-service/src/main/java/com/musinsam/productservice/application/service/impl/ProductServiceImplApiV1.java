package com.musinsam.productservice.application.service.impl;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.common.user.UserRoleType;
import com.musinsam.productservice.application.dto.request.ReqProductPostDtoApiV1;
import com.musinsam.productservice.application.dto.request.ReqProductPutByProductIdDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetByProductIdDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetStockDtoApiV1;
import com.musinsam.productservice.application.service.ProductServiceApiV1;
import com.musinsam.productservice.domain.product.entity.ProductEntity;
import com.musinsam.productservice.domain.product.entity.ProductImageEntity;
import com.musinsam.productservice.domain.product.repository.ProductImageRepository;
import com.musinsam.productservice.domain.product.repository.ProductRepository;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    ProductEntity product = findProductEntityById(productId);

    List<ProductImageEntity> productImages = productImageRepository.findByProductIdAndDeletedAtIsNull(
        productId);

    // TODO: shop feign client 호출
    String shopName = "상점이름1";

    ResProductGetByProductIdDtoApiV1 resDto = ResProductGetByProductIdDtoApiV1.of(product,
        productImages, shopName);

    return resDto;
  }

  @Override
  public ResProductGetDtoApiV1 getProductList(int page, int size) {

    PageRequest pageRequest = PageRequest.of(page - 1, size);
    Page<ProductEntity> productEntityPage = productRepository.findByDeletedAtIsNull(
        pageRequest);

    return ResProductGetDtoApiV1.of(productEntityPage);
  }

  @Override
  @Transactional
  public void updateProduct(CurrentUserDtoApiV1 currentUser, UUID productId,
      ReqProductPutByProductIdDtoApiV1 dto) {

    ProductEntity product = findProductEntityById(productId);

    // TODO: ROLE_COMPANY == shop 확인
    validateShopManager(currentUser, product);

    dto.getProduct().updateOf(product);
  }

  @Override
  @Transactional
  public void deleteProduct(CurrentUserDtoApiV1 currentUser, UUID productId) {

    ProductEntity product = findProductEntityById(productId);

    // TODO: ROLE_COMPANY == shop 확인
    validateShopManager(currentUser, product);

    product.softDelete(currentUser.userId(), ZoneId.systemDefault());

  }


  @Override
  public ResProductGetStockDtoApiV1 getProductStock(CurrentUserDtoApiV1 currentUser,
      UUID productId) {

    ProductEntity product = findProductEntityById(productId);
    validateShopManager(currentUser, product);

    return ResProductGetStockDtoApiV1.of(product);
  }


  private ProductEntity findProductEntityById(UUID productId) {
    return productRepository.findByIdAndDeletedAtIsNull(productId)
        .orElseThrow(() -> new RuntimeException());
  }

  private void validateShopManager(CurrentUserDtoApiV1 currentUser, ProductEntity product) {
    if ((UserRoleType.ROLE_COMPANY).equals(currentUser.role())) {
      // USER-ROLE이 ROLE_COMPANY일 경우,
      // product의 shopId로 업체관리자가 UserId와 같은지 확인
      // TODO: shop feign client 호출 (shopId보내서 UserId 받기)
      UUID shopId = product.getShopId();

      // 다르면 예외
    }
  }
}
