package com.musinsam.eventservice.domain.event.repository;

import com.musinsam.eventservice.domain.event.entity.EventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {

}
