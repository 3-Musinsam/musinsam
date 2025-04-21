package com.musinsam.shopservice.application.service;

import com.musinsam.common.exception.CustomException;
import com.musinsam.shopservice.application.dto.request.ReqShopGetSearchDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResInternalShopGetByShopIdDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopCouponDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopGetDtoApiV1;
import com.musinsam.shopservice.domain.shop.entity.ShopEntity;
import com.musinsam.shopservice.domain.shop.repository.ShopQueryRepository;
import com.musinsam.shopservice.domain.shop.repository.ShopRepository;
import com.musinsam.shopservice.infrastructure.excepcion.ShopErrorCode;
import com.musinsam.shopservice.infrastructure.feign.CouponFeignClientApiV1;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopInternalServiceApiV1 {

  private final ShopRepository shopRepository;
  private final ShopQueryRepository shopQueryRepository;
  private final CouponFeignClientApiV1 couponFeignClientApiV1;

  /**
   * 내부 호출용
   */
  @Transactional
  public ResInternalShopGetByShopIdDtoApiV1 internalGetShop(UUID id) {
    ShopEntity shopEntity = shopRepository.findById(id)
        .orElseThrow(() -> new CustomException(ShopErrorCode.SHOP_NOT_FOUND));
    return ResInternalShopGetByShopIdDtoApiV1.of(shopEntity);
  }

  @Transactional(readOnly = true)
  public ResShopGetDtoApiV1 internalGetShopListSearch(Pageable pageable,
      ReqShopGetSearchDtoApiV1.Shop dto) {
    Page<ShopEntity> shopEntityPage = shopQueryRepository.findShopSearchList(pageable, dto);
    return ResShopGetDtoApiV1.of(shopEntityPage);
  }

  @Transactional(readOnly = true)
  public ResShopCouponDtoApiV1 internalCouponGetByShopId(UUID shopId) {
    return couponFeignClientApiV1.getCouponsByCompanyId(shopId);
  }

  @Transactional
  public Boolean internalExistsShopById(UUID id) {
    return shopRepository.existsById(id);
  }
}
