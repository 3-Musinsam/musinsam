package com.musinsam.alarmservice.presentation.controller;

import com.musinsam.alarmservice.application.dto.request.ReqAlarmPostDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmGetByIdDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmGetDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmPostDtoApiV1;
import com.musinsam.alarmservice.application.service.AlarmInternalServiceApiV1;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/v1/slack-alarms")
public class AlarmInternalControllerApiV1 {

  private final AlarmInternalServiceApiV1 alarmInternalServiceApiV1;

  /**
   * Creates a new alarm using the provided request data.
   *
   * @param dto the alarm creation request payload
   * @return the created alarm information wrapped in a response entity
   */
  @PostMapping
  public ResponseEntity<ResAlarmPostDtoApiV1> postBy(@Valid @RequestBody ReqAlarmPostDtoApiV1 dto) {
    return ResponseEntity.ok(alarmInternalServiceApiV1.postBy(dto));
  }

  /**
   * Retrieves a paginated list of alarms.
   *
   * @param pageable pagination and sorting information
   * @return a response entity containing the paginated alarm data
   */
  @GetMapping
  public ResponseEntity<ResAlarmGetDtoApiV1> getBy(@Valid Pageable pageable) {
    return ResponseEntity.ok(alarmInternalServiceApiV1.getBy(pageable));
  }

  /**
   * Retrieves the details of a specific alarm by its UUID.
   *
   * @param id the unique identifier of the alarm to retrieve
   * @return a ResponseEntity containing the alarm details
   */
  @GetMapping("/{id}")
  public ResponseEntity<ResAlarmGetByIdDtoApiV1> getById(@Valid @PathVariable UUID id) {
    return ResponseEntity.ok(alarmInternalServiceApiV1.getById(id));
  }
}
