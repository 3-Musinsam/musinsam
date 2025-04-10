package com.musinsam.eventservice.application.dto.response;

import com.musinsam.eventservice.domain.event.EventStatus;
import com.musinsam.eventservice.domain.event.entity.EventProductEntity;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResEventGetProductDtoApiV1 {

  private EventProduct eventProduct;

  public static ResEventGetProductDtoApiV1 of(EventProductEntity eventProductEntity) {
    return ResEventGetProductDtoApiV1.builder()
        .eventProduct(EventProduct.from(eventProductEntity))
        .build();
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class EventProduct {

    private UUID id;
    private UUID eventId;
    private UUID productId;
    private BigDecimal originPrice;
    private BigDecimal salePrice;
    private Integer discountRate;
    private Integer maxPurchase;
    private Integer soldQuantity;
    private EventStatus status;

    public static EventProduct from(EventProductEntity eventProductEntity) {
      return EventProduct.builder()
          .id(null)
          .eventId(null)
          .productId(null)
          .originPrice(null)
          .salePrice(null)
          .discountRate(null)
          .maxPurchase(null)
          .soldQuantity(null)
          .status(null)
          .build();
    }
  }

}
