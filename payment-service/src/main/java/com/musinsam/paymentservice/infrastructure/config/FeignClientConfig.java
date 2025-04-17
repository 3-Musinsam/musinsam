package com.musinsam.paymentservice.infrastructure.config;

import com.musinsam.paymentservice.infrastructure.client.OrderFeignClient;
import com.musinsam.paymentservice.infrastructure.client.TossPaymentFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
    OrderFeignClient.class,
    TossPaymentFeignClient.class
})
public class FeignClientConfig {

}
