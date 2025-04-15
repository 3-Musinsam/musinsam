package com.musinsam.productservice.domain.product.repository;

import com.musinsam.productservice.domain.product.entity.ProductCouponEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCouponRespository extends JpaRepository<ProductCouponEntity, UUID> {

}
