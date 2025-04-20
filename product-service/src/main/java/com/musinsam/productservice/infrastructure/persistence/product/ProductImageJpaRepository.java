package com.musinsam.productservice.infrastructure.persistence.product;

import com.musinsam.productservice.domain.product.entity.ProductImageEntity;
import com.musinsam.productservice.domain.product.repository.ProductImageRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageJpaRepository extends JpaRepository<ProductImageEntity, UUID>,
    ProductImageRepository {

}
