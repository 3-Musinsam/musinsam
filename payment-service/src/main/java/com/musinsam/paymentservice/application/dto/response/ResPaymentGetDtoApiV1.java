package com.musinsam.paymentservice.application.dto.response;

import com.musinsam.paymentservice.application.dto.response.ResPaymentGetDtoApiV1.PaymentPage.Payment;
import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResPaymentGetDtoApiV1 {

  private PaymentPage paymentPage;

  public static ResPaymentGetDtoApiV1 of(Page<PaymentEntity> paymentEntityPage) {
    return ResPaymentGetDtoApiV1.builder()
        .paymentPage(new PaymentPage(paymentEntityPage))
        .build();
  }

  @Getter
  @ToString
  public static class PaymentPage extends PagedModel<Payment> {

    public PaymentPage(Page<PaymentEntity> paymentEntityPage) {
      super(
          new PageImpl<>(
              Payment.from(paymentEntityPage.getContent()),
              paymentEntityPage.getPageable(),
              paymentEntityPage.getTotalElements()
          )
      );
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payment {

      private UUID id;
      private UUID orderId;
      private String paymentStatus;
      private String paymentProvider;
      private BigDecimal totalAmount;
      private BigDecimal discountAmount;
      private BigDecimal finalAmount;
      private ZonedDateTime createdAt;

      public static List<Payment> from(List<PaymentEntity> paymentEntities) {
        return paymentEntities.stream()
            .map(Payment::from)
            .collect(Collectors.toList());
      }

      public static Payment from(PaymentEntity paymentEntity) {
        return Payment.builder()
            // TODO:
            .build();
      }
    }
  }
}
