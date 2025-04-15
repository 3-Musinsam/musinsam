package com.musinsam.productservice.domain.product.repository;

import com.musinsam.productservice.domain.product.entity.ProductImageEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, UUID> {

  List<ProductImageEntity> findByProductIdAndDeletedAtIsNull(UUID productId);
}
