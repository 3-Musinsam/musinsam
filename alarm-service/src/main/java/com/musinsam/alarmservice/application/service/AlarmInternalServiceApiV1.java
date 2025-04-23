package com.musinsam.alarmservice.application.service;

import com.musinsam.alarmservice.application.dto.request.ReqAlarmPostDtoApiV1;
import com.musinsam.alarmservice.application.dto.request.SlackRequest;
import com.musinsam.alarmservice.application.dto.response.ResAlarmGetByIdDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmGetDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.ResAlarmPostDtoApiV1;
import com.musinsam.alarmservice.application.dto.response.SlackOpenImResponse;
import com.musinsam.alarmservice.domain.alarm.entity.AlarmEntity;
import com.musinsam.alarmservice.domain.alarm.repository.AlarmRepository;
import com.musinsam.alarmservice.infrastructure.exception.AlarmErrorCode;
import com.musinsam.alarmservice.infrastructure.feign.SlackFeignClientApiV1;
import com.musinsam.common.exception.CustomException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmInternalServiceApiV1 {

  private final AlarmRepository alarmRepository;
  private final SlackFeignClientApiV1 slackFeignClientApiV1;
  private final SlackService slackService;

  /**
   * Creates a new alarm from the provided DTO, persists it, and sends its message to Slack.
   *
   * @param dto the request DTO containing alarm details
   * @return a response DTO representing the saved alarm
   */
  @Transactional
  public ResAlarmPostDtoApiV1 postBy(ReqAlarmPostDtoApiV1 dto) {
    AlarmEntity alarmEntity = alarmRepository.save(dto.getAlarm().toEntity());
    slackFeignClientApiV1.sendSlack(new SlackRequest(alarmEntity.getMessage()));
    return ResAlarmPostDtoApiV1.of(alarmEntity);
  }

  @Transactional(readOnly = true)
  public ResAlarmGetDtoApiV1 getBy(Pageable pageable) {

    Page<AlarmEntity> alarmEntityPage;
    alarmEntityPage = alarmRepository.findByDeletedAtIsNullOrderByIdDesc(
        pageable);
    return ResAlarmGetDtoApiV1.of(alarmEntityPage);
  }

  /**
   * Retrieves an alarm by its unique identifier.
   *
   * @param id the UUID of the alarm to retrieve
   * @return a response DTO representing the found alarm
   * @throws CustomException if no alarm with the given ID exists
   */
  @Transactional(readOnly = true)
  public ResAlarmGetByIdDtoApiV1 getById(UUID id) {
    AlarmEntity alarmEntity = alarmRepository.findById(id)
        .orElseThrow(() -> new CustomException(AlarmErrorCode.ALARM_NOT_FOUND));
    return ResAlarmGetByIdDtoApiV1.of(alarmEntity);
  }

  /**
   * Sends a direct Slack message to a specific user notifying them of a new product arrival.
   *
   * @param dto the Slack open IM response containing context for the message
   */
  @Transactional
  public void postById(SlackOpenImResponse dto) {
    slackService.sendDmToUser(dto.getChannel().getId(), "🚨 [입고알림] 새 상품이 입고되었습니다!");
  }
}
