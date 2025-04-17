package com.musinsam.paymentservice.domain.repository;

import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository {

  public PaymentEntity save(PaymentEntity paymentEntity);

  Optional<PaymentEntity> findByPaymentKey(String paymentKey);

  Optional<PaymentEntity> findById(UUID paymentId);

  Page<PaymentEntity> findAll(Predicate predicate, Pageable pageable);
}
