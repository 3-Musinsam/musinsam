package com.musinsam.alarmservice.domain.alarm.repository;

import com.musinsam.alarmservice.domain.alarm.entity.AlarmEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<AlarmEntity, UUID> {

  Page<AlarmEntity> findByDeletedAtIsNullOrderByIdDesc(Pageable pageable);
}
