package com.musinsam.orderservice.infrastructure.config;

import com.musinsam.orderservice.infrastructure.client.ProductFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
    ProductFeignClient.class
})
public class FeignClientConfig {

}
