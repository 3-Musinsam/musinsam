package com.musinsam.shopservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class ShopServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShopServiceApplication.class, args);
  }
}
