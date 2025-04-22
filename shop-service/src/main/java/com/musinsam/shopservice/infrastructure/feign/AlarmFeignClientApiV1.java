package com.musinsam.shopservice.infrastructure.feign;

import com.musinsam.common.config.FeignConfig;
import com.musinsam.shopservice.application.dto.request.ReqAlarmPostDtoApiV1;
import com.musinsam.shopservice.application.dto.response.ResAlarmPostDtoApiV1;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "alarm-service", configuration = FeignConfig.class)
public interface AlarmFeignClientApiV1 {

  @PostMapping("/internal/v1/slack-alarms")
  ResponseEntity<ResAlarmPostDtoApiV1> postBy(@RequestBody ReqAlarmPostDtoApiV1 dto);
}
