package com.musinsam.userservice.app.global.response;


import static org.springframework.http.HttpStatus.OK;

import com.musinsam.common.response.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserResponseCode implements SuccessCode {

  USER_GET_SUCCESS(0, "User retrieved successfully.", OK),
  USERS_GET_SUCCESS(0, "User list retrieved successfully.", OK),
  USER_DELETE_BY_ID_SUCCESS(0, "User deleted successfully by ID.", OK),
  USER_PATCH_ROLE_BY_ID_SUCCESS(0, "User role updated successfully by ID.", OK);

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;
}
