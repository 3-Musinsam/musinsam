package com.musinsam.shopservice.domain.shop.repository;

import com.musinsam.shopservice.domain.shop.entity.ShopEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<ShopEntity, UUID> {

  Page<ShopEntity> findByDeletedAtIsNullOrderByIdDesc(Pageable pageable);
}
