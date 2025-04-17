package com.musinsam.paymentservice.infrastructure.persistence;

import com.musinsam.paymentservice.domain.payment.entity.PaymentEntity;
import com.musinsam.paymentservice.domain.payment.entity.QPaymentEntity;
import com.musinsam.paymentservice.domain.repository.PaymentRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentJpaRepository extends
    JpaRepository<PaymentEntity, UUID>,
    PaymentRepository,
    QuerydslPredicateExecutor<PaymentEntity>,
    QuerydslBinderCustomizer<QPaymentEntity> {


  @Override
  Optional<PaymentEntity> findById(UUID paymentId);

  @Override
  default void customize(QuerydslBindings bindings, @NotNull QPaymentEntity qPaymentEntity) {
    bindings.bind(String.class).all((StringPath path, Collection<? extends String> values) -> {
      List<String> valueList = new ArrayList<>(values.stream().map(String::trim).toList());

      if (valueList.isEmpty()) {
        return Optional.empty();
      }

      BooleanBuilder builder = new BooleanBuilder();

      for (String value : valueList) {
        builder.or(path.containsIgnoreCase(value));
      }

      return Optional.of(builder);
    });
  }
}
