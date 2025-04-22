package com.musinsam.shopservice.application.service;

import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import com.musinsam.shopservice.application.dto.request.ReqShopPostDtoApiV1;
import com.musinsam.shopservice.application.dto.request.ReqShopPutByShopIdDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopDeleteByShopIdDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopGetByShopIdDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopGetDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopPostDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResShopPutByShopIdDtoApiV1;
import com.musinsam.shopservice.domain.shop.entity.ShopEntity;
import com.musinsam.shopservice.domain.shop.repository.ShopRepository;
import com.musinsam.shopservice.infrastructure.excepcion.ShopErrorCode;
import java.time.ZoneId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopServiceApiV1 {

  private final ShopRepository shopRepository;

  /**
   * Creates a new shop entity from the provided data and returns its representation.
   *
   * @param dto the data transfer object containing shop creation details
   * @return a response DTO representing the newly created shop
   */
  @Transactional
  public ResShopPostDtoApiV1 postBy(ReqShopPostDtoApiV1 dto, CurrentUserDtoApiV1 currentUser) {
    ShopEntity shopEntity = shopRepository.save(dto.getShop().toEntity());
    return ResShopPostDtoApiV1.of(shopEntity);
  }

  @Transactional(readOnly = true)
  public ResShopGetDtoApiV1 getBy(Pageable pageable,
      CurrentUserDtoApiV1 currentUser) {

    Page<ShopEntity> shopEntityPage;
    shopEntityPage = shopRepository.findByDeletedAtIsNullOrderByIdDesc(
        pageable);
    return ResShopGetDtoApiV1.of(shopEntityPage);
  }

  @Transactional(readOnly = true)
  public ResShopGetByShopIdDtoApiV1 getByShopId(UUID id, CurrentUserDtoApiV1 currentUser) {
    ShopEntity shopEntity = shopRepository.findById(id)
        .orElseThrow(() -> new CustomException(ShopErrorCode.SHOP_NOT_FOUND));
    return ResShopGetByShopIdDtoApiV1.of(shopEntity);
  }

  @Transactional
  public ResShopPutByShopIdDtoApiV1 putByShopId(UUID id, ReqShopPutByShopIdDtoApiV1 dto,
      CurrentUserDtoApiV1 currentUser) {
    ShopEntity shopEntity = shopRepository.findById(id)
        .orElseThrow(() -> new CustomException(ShopErrorCode.SHOP_NOT_FOUND));
    shopRepository.save(dto.getShop().updateOf());
    return ResShopPutByShopIdDtoApiV1.of(shopEntity);
  }

  @Transactional
  public ResShopDeleteByShopIdDtoApiV1 deleteByShopId(UUID id, CurrentUserDtoApiV1 currentUser) {
    ShopEntity shopEntity = shopRepository.findById(id)
        .orElseThrow(() -> new CustomException(ShopErrorCode.SHOP_NOT_FOUND));
    shopEntity.softDelete(currentUser.userId(), ZoneId.of("Asia/Seoul"));
    return ResShopDeleteByShopIdDtoApiV1.of(shopEntity);
  }
}
