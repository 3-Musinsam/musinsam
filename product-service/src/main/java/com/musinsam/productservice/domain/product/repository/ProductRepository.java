package com.musinsam.productservice.domain.product.repository;

import com.musinsam.productservice.domain.product.entity.ProductEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductRepository {

  Optional<ProductEntity> findByIdAndDeletedAtIsNull(UUID productId);

  Page<ProductEntity> findByDeletedAtIsNull(PageRequest pageRequest);

  ProductEntity save(ProductEntity product);
}
