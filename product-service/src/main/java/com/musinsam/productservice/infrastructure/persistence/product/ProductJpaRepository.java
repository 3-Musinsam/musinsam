package com.musinsam.productservice.infrastructure.persistence.product;

import com.musinsam.productservice.domain.product.entity.ProductEntity;
import com.musinsam.productservice.domain.product.repository.ProductRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID>,
    ProductRepository {

}
