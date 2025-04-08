package com.musinsam.common.domain;

import com.musinsam.common.time.TimeZoneContextHolder;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public abstract class BaseEntity {

  @CreatedBy
  @Column(updatable = false)
  private Long createdBy;

  @CreatedDate
  @Column(updatable = false)
  private ZonedDateTime createdAt;

  @LastModifiedBy
  private Long updatedBy;

  @LastModifiedDate
  private ZonedDateTime updatedAt;

  private Long deletedBy;
  private ZonedDateTime deletedAt;

  public void softDelete(Long userId, ZoneId zoneId) {
    this.deletedAt = ZonedDateTime.now(zoneId);
    this.deletedBy = userId;
  }
}