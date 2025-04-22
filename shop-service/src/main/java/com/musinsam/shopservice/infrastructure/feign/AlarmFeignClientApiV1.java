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

  /**
   * Sends an alarm notification request to the alarm service via a POST call.
   *
   * @param dto the alarm request payload to be sent
   * @return the response from the alarm service containing the result of the alarm post operation
   */
  @PostMapping("/internal/v1/slack-alarms")
  ResponseEntity<ResAlarmPostDtoApiV1> postBy(@RequestBody ReqAlarmPostDtoApiV1 dto);
}
