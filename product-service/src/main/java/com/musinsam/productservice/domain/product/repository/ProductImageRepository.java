package com.musinsam.productservice.domain.product.repository;

import com.musinsam.productservice.domain.product.entity.ProductImageEntity;
import java.util.List;
import java.util.UUID;

public interface ProductImageRepository {

  List<ProductImageEntity> findByProductIdAndDeletedAtIsNull(UUID productId);

  ProductImageEntity save(ProductImageEntity productImage);
}
