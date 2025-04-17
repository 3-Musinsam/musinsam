package com.musinsam.productservice.infrastructure.s3.service;

import com.musinsam.productservice.infrastructure.s3.S3Folder;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

  void uploadFile(final String fileName, S3Folder s3Folder, MultipartFile multipartFile);

  void removeFile(final String fileName, S3Folder s3Folder);

}
