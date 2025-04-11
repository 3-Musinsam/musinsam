package com.musinsam.alarmservice.presentation.controller;

import static com.musinsam.common.user.UserRoleType.ROLE_MASTER;
import static com.musinsam.common.user.UserRoleType.ROLE_USER;

import com.musinsam.alarmservice.application.dto.request.ReqAlarmPostDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmGetByIdDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmGetDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmPostDtoApiV1;
import com.musinsam.alarmservice.application.service.AlarmServiceApiV1;
import com.musinsam.common.aop.CustomPreAuthorize;
import com.musinsam.common.resolver.CurrentUser;
import com.musinsam.common.response.ApiResponse;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Alarm Service", description = "알림 서비스 API")
@RequestMapping("/v1/slack-alarms")
public class AlarmControllerApiV1 {

  private final AlarmServiceApiV1 alarmServiceApiV1;

  @Operation(summary = "알림 등록", description = "알림 등록 api 입니다.")
  @CustomPreAuthorize(userRoleType = {ROLE_USER})
  @PostMapping
  public ResponseEntity<ApiResponse<ResAlarmPostDtoApiV1>> postBy(@Valid
  @RequestBody ReqAlarmPostDtoApiV1 dto,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return new ResponseEntity<>(
        ApiResponse.<ResAlarmPostDtoApiV1>builder()
            .code(ApiResponse.success().getCode())
            .message("The alarm was successfully sent.")
            .data(alarmServiceApiV1.postBy(dto, currentUser))
            .build(),
        HttpStatus.OK
    );
  }

  @Operation(summary = "알림 목록 조회", description = "알림 목록 조회 api 입니다.")
  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_MASTER})
  @GetMapping
  public ResponseEntity<ApiResponse<ResAlarmGetDtoApiV1>> getBy(
      @Valid Pageable pageable,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return new ResponseEntity<>(
        ApiResponse.<ResAlarmGetDtoApiV1>builder()
            .code(ApiResponse.success().getCode())
            .message("Alarm full query succeeded.")
            .data(alarmServiceApiV1.getBy(pageable, currentUser))
            .build(),
        HttpStatus.OK
    );
  }

  @Operation(summary = "알림 단건 조회", description = "알림 단건 조회 api 입니다.")
  @CustomPreAuthorize(userRoleType = {ROLE_USER, ROLE_MASTER})
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<ResAlarmGetByIdDtoApiV1>> getById(@Valid @PathVariable UUID id,
      @CurrentUser CurrentUserDtoApiV1 currentUser
  ) {
    return new ResponseEntity<>(
        ApiResponse.<ResAlarmGetByIdDtoApiV1>builder()
            .code(ApiResponse.success().getCode())
            .message("Alarm single query was successful.")
            .data(alarmServiceApiV1.getById(id, currentUser))
            .build(),
        HttpStatus.OK
    );
  }
}
