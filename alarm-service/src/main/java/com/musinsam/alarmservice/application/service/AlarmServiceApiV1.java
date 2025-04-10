package com.musinsam.alarmservice.application.service;

import com.musinsam.alarmservice.application.dto.request.ReqAlarmPostDtoApiV1;
import com.musinsam.alarmservice.application.dto.request.SlackRequest;
import com.musinsam.alarmservice.application.dto.response.ResAlarmGetByIdDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmGetDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmPostDtoApiV1;
import com.musinsam.alarmservice.domain.alarm.entity.AlarmEntity;
import com.musinsam.alarmservice.domain.alarm.repository.AlarmRepository;
import com.musinsam.alarmservice.infrastructure.client.SlackClient;
import com.musinsam.alarmservice.infrastructure.exception.AlarmErrorCode;
import com.musinsam.common.exception.CustomException;
import com.musinsam.common.user.CurrentUserDtoApiV1;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmServiceApiV1 {

  private final AlarmRepository alarmRepository;
  private final SlackClient slackClient;

  @Transactional
  public ResAlarmPostDtoApiV1 postBy(ReqAlarmPostDtoApiV1 dto, CurrentUserDtoApiV1 currentUser) {
    AlarmEntity alarmEntity = alarmRepository.save(dto.getAlarm().toEntity());
    slackClient.sendSlack(new SlackRequest(alarmEntity.getMessage()));
    return ResAlarmPostDtoApiV1.of(alarmEntity);
  }

  public ResAlarmGetDtoApiV1 getBy(Pageable pageable,
      CurrentUserDtoApiV1 currentUser) {

    Page<AlarmEntity> alarmEntityPage;
    alarmEntityPage = alarmRepository.findByDeletedAtIsNullOrderByIdDesc(
        pageable);
    return ResAlarmGetDtoApiV1.of(alarmEntityPage);
  }

  public ResAlarmGetByIdDtoApiV1 getById(UUID id, CurrentUserDtoApiV1 currentUser) {
    AlarmEntity alarmEntity = alarmRepository.findById(id)
        .orElseThrow(() -> new CustomException(AlarmErrorCode.ALARM_NOT_FOUND));
    return ResAlarmGetByIdDtoApiV1.of(alarmEntity);
  }
}
