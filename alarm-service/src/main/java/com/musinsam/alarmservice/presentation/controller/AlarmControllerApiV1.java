package com.musinsam.alarmservice.presentation.controller;

import com.musinsam.alarmservice.application.dto.request.ReqAlarmPostDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmGetByIdDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmGetDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmPostDtoApiV1;
import com.musinsam.alarmservice.application.service.AlarmServiceApiV1;
import com.musinsam.common.response.ApiResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/slack-alarms")
public class AlarmControllerApiV1 {

  public AlarmServiceApiV1 alarmServiceApiV1;

  @PostMapping
  public ResponseEntity<ApiResponse<ResAlarmPostDtoApiV1>> postBy(
      @RequestBody ReqAlarmPostDtoApiV1 dto
  ) {
    return new ResponseEntity<>(
        ApiResponse.<ResAlarmPostDtoApiV1>builder()
            .code(ApiResponse.success().getCode())
            .message("The alarm was successfully sent.")
            .data(alarmServiceApiV1.postBy(dto))
            .build(),
        HttpStatus.OK
    );
  }

  @GetMapping
  public ResponseEntity<ApiResponse<ResAlarmGetDtoApiV1>> getBy(
      Pageable pageable
  ) {
    return new ResponseEntity<>(
        ApiResponse.<ResAlarmGetDtoApiV1>builder()
            .code(ApiResponse.success().getCode())
            .message("Alarm full query succeeded.")
            .data(alarmServiceApiV1.getBy(pageable))
            .build(),
        HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ResAlarmGetByIdDtoApiV1>> getById(
      UUID id
  ) {
    return new ResponseEntity<>(
        ApiResponse.<ResAlarmGetByIdDtoApiV1>builder()
            .code(ApiResponse.success().getCode())
            .message("Alarm single query was successful.")
            .data(alarmServiceApiV1.getById(id))
            .build(),
        HttpStatus.OK
    );
  }
}
