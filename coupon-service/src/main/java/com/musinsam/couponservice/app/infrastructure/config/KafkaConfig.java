package com.musinsam.couponservice.app.infrastructure.config;

import com.musinsam.couponservice.app.application.dto.v4.coupon.response.IssueMessage;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {
  private static final String BOOTSTRAP_SERVERS = "localhost:19092";
  private static final String GROUP_ID = "coupon-service";

  @Bean
  public ProducerFactory<String, IssueMessage> couponProducerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true); // Header 타입 정보 추가

    config.put(ProducerConfig.ACKS_CONFIG, "all"); // 안정 설정 추가
    config.put(ProducerConfig.RETRIES_CONFIG, 3);
    config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
    return new DefaultKafkaProducerFactory<>(config);
  }

  @Bean
  public KafkaTemplate<String, IssueMessage> couponKafkaTemplate() {
    return new KafkaTemplate<>(couponProducerFactory());
  }

  @Bean
  public ConsumerFactory<String, IssueMessage> couponConsumerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 안정 설정 추가
    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);

    JsonDeserializer<IssueMessage> jsonDeserializer = new JsonDeserializer<>(IssueMessage.class);
    jsonDeserializer.addTrustedPackages("*");
    jsonDeserializer.setUseTypeMapperForKey(true); // Type Mapper 활성화
    jsonDeserializer.setRemoveTypeHeaders(false);  // Header 유지

    return new DefaultKafkaConsumerFactory<>(
        config,
        new StringDeserializer(),
        jsonDeserializer
    );
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, IssueMessage> couponKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, IssueMessage> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(couponConsumerFactory());
    factory.setConcurrency(3); // 동시성 설정
    return factory;
  }
}
