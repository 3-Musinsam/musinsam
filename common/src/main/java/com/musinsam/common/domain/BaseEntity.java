package com.musinsam.common.domain;

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
  @Column(name = "created_by", updatable = false)
  private Long createdBy;

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  private ZonedDateTime createdAt;

  @LastModifiedBy
  @Column(name = "updated_by")
  private Long updatedBy;

  @LastModifiedDate
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;

  @Column(name = "deleted_by")
  private Long deletedBy;

  @Column(name = "deleted_at")
  private ZonedDateTime deletedAt;

  public void softDelete(Long userId, ZoneId zoneId) {
    this.deletedAt = ZonedDateTime.now(zoneId);
    this.deletedBy = userId;
  }
}