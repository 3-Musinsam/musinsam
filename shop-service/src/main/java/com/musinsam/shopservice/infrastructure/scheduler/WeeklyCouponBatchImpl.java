package com.musinsam.shopservice.infrastructure.scheduler;

import com.musinsam.shopservice.application.dto.response.ResInternalShopGetDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResInternalShopGetDtoApiV1.ShopPage.Shop;
import com.musinsam.shopservice.application.service.ShopInternalServiceApiV1;
import com.musinsam.shopservice.infrastructure.feign.AiFeignClientApiV1;
import com.musinsam.shopservice.infrastructure.kafka.AlarmKafkaProducer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class WeeklyCouponBatchImpl extends WeeklyCouponBatch {

  private final ShopInternalServiceApiV1 shopInternalServiceApiV1;

  /**
   * Constructs a WeeklyCouponBatchImpl with the required service dependencies.
   *
   * @param shopInternalServiceApiV1 service for retrieving shop information
   * @param aiFeignClientApiV1 AI client for external API interactions
   * @param alarmKafkaProducer producer for sending alarm notifications
   */
  public WeeklyCouponBatchImpl(
      ShopInternalServiceApiV1 shopInternalServiceApiV1,
      AiFeignClientApiV1 aiFeignClientApiV1,
      AlarmKafkaProducer alarmKafkaProducer
  ) {
    super(shopInternalServiceApiV1, aiFeignClientApiV1, alarmKafkaProducer);
    this.shopInternalServiceApiV1 = shopInternalServiceApiV1;
  }

  /**
   * Retrieves up to 100 shop UUIDs from the internal shop service for batch processing.
   *
   * @return a list of shop UUIDs, limited to the first 100 shops
   */
  @Override
  protected List<UUID> getShopIds() {
    // 최대 100개 상점만 페이징 조회
    PageRequest pageable = PageRequest.of(0, 100);
    ResInternalShopGetDtoApiV1 res = shopInternalServiceApiV1.internalGetShopList(pageable);

    return res.getShopPage().getContent().stream() // PagedModel의 getContent()
        .map(Shop::getId)
        .collect(Collectors.toList());
  }
}