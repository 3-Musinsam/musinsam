package com.musinsam.paymentservice.infrastructure.client.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResTossPaymentCancelDto {

  private BigDecimal cancelAmount;
  private String cancelReason;
}
