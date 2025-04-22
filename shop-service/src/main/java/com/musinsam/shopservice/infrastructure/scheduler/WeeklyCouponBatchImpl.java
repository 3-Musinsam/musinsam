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

  // 생성자 주입을 위해 부모 클래스의 생성자에도 넘겨줌
  public WeeklyCouponBatchImpl(
      ShopInternalServiceApiV1 shopInternalServiceApiV1,
      AiFeignClientApiV1 aiFeignClientApiV1,
      AlarmKafkaProducer alarmKafkaProducer
  ) {
    super(shopInternalServiceApiV1, aiFeignClientApiV1, alarmKafkaProducer);
    this.shopInternalServiceApiV1 = shopInternalServiceApiV1;
  }

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