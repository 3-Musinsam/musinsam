package com.musinsam.userservice.app.domain.user.entity;

import com.musinsam.common.domain.BaseEntity;
import com.musinsam.userservice.app.domain.user.vo.UserRoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_user")
@Entity
public class UserEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "email", nullable = false, length = 100)
  private String email;

  @Column(name = "password", nullable = false, length = 512)
  private String password;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private UserRoleType userRoleType;

  private UserEntity(String email, String password, String name, UserRoleType userRoleType) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.userRoleType = userRoleType;
  }

  public static UserEntity of(String email, String password, String name, UserRoleType userRoleType) {
    return new UserEntity(email, password, name, userRoleType);
  }

  public static UserEntity of(String email, String password, String name) {
    return new UserEntity(email, password, name, UserRoleType.ROLE_USER);
  }

  @Override
  public void softDelete(Long userId, ZoneId zoneId) {
    super.softDelete(userId, zoneId);
  }
}
