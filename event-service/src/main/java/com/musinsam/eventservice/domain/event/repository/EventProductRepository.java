package com.musinsam.eventservice.domain.event.repository;

import com.musinsam.eventservice.domain.event.entity.EventProductEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventProductRepository extends JpaRepository<EventProductEntity, UUID> {

}
