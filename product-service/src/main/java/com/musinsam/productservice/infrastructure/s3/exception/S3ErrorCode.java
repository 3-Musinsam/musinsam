package com.musinsam.productservice.infrastructure.s3.exception;

import com.musinsam.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum S3ErrorCode implements ErrorCode {

  S3_FOLDER_NOT_SPECIFIED(HttpStatus.BAD_REQUEST, "S3 저장 경로가 지정되지 않았습니다.", -6),
  FILE_IS_EMPTY(HttpStatus.BAD_REQUEST, "파일이 비어있거나 존재하지 않습니다.", -7),
  S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 오류가 발생했습니다.", -8),
  S3_Deleted_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제 중 오류가 발생했습니다.", -9);

  private final HttpStatus httpStatus;
  private final String message;
  private final Integer code;
}
