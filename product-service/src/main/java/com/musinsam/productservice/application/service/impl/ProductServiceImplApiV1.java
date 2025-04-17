package com.musinsam.productservice.application.service.impl;

import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.common.user.UserRoleType;
import com.musinsam.productservice.application.dto.request.ReqProductPatchByProductIdDtoApiV1;
import com.musinsam.productservice.application.dto.request.ReqProductPostDtoApiV1;
import com.musinsam.productservice.application.dto.request.ReqProductPutByProductIdDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetByProductIdDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetCouponDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetStockDtoApiV1;
import com.musinsam.productservice.application.service.ProductServiceApiV1;
import com.musinsam.productservice.domain.product.entity.ProductEntity;
import com.musinsam.productservice.domain.product.entity.ProductImageEntity;
import com.musinsam.productservice.domain.product.repository.ProductImageRepository;
import com.musinsam.productservice.domain.product.repository.ProductRepository;
import com.musinsam.productservice.infrastructure.dto.res.ResShopCouponDto;
import com.musinsam.productservice.infrastructure.image.S3Folder;
import com.musinsam.productservice.infrastructure.image.service.FileService;
import com.musinsam.productservice.infrastructure.image.service.S3Service;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImplApiV1 implements ProductServiceApiV1 {

  private final ProductRepository productRepository;
  private final ProductImageRepository productImageRepository;
  private final FileService fileService;
  private final S3Service s3Service;

  @Override
  @Transactional
  public void createProduct(CurrentUserDtoApiV1 currentUser, ReqProductPostDtoApiV1 dto,
      List<MultipartFile> images) {

    ProductEntity product = dto.getProduct().toEntity();
    productRepository.save(product);

    for (MultipartFile image : images) {
      fileService.saveImageFile(S3Folder.PRODUCT, image, product.getId());
    }
  }

  @Override
  @Transactional(readOnly = true)
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
  @Transactional(readOnly = true)
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

    List<ProductImageEntity> images = productImageRepository.findByProductIdAndDeletedAtIsNull(
        productId);

    product.softDelete(currentUser.userId(), ZoneId.systemDefault());

    for (ProductImageEntity productImage : images) {
      s3Service.removeFile(productImage.getImageUrl(), productImage.getS3Folder());
      productImage.softDelete(currentUser.userId(), ZoneId.systemDefault());
    }

  }


  @Override
  @Transactional(readOnly = true)
  public ResProductGetStockDtoApiV1 getProductStock(CurrentUserDtoApiV1 currentUser,
      UUID productId) {

    ProductEntity product = findProductEntityById(productId);
    validateShopManager(currentUser, product);

    return ResProductGetStockDtoApiV1.of(product);
  }


  @Override
  @Transactional
  public void updateProductStock(CurrentUserDtoApiV1 currentUser, UUID productId,
      ReqProductPatchByProductIdDtoApiV1 dto) {

    ProductEntity product = findProductEntityById(productId);
    validateShopManager(currentUser, product);

    dto.getProduct().updateOf(product);
  }

  @Override
  @Transactional(readOnly = true)
  public ResProductGetCouponDtoApiV1 getProductCouponList(UUID productId, int size, int page) {

    ProductEntity product = findProductEntityById(productId);

    UUID shopId = product.getShopId();
    // TODO: shop-service feign client 호출해서 쿠폰 페이지 받기
    // return ResShopCouponDto

    ResShopCouponDto resDto = null;

    return ResProductGetCouponDtoApiV1.of(resDto.getCouponList(), productId, page, size);
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
