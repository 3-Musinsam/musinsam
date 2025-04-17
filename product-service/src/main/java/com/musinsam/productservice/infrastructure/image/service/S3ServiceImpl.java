package com.musinsam.productservice.infrastructure.image.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.musinsam.productservice.infrastructure.image.S3Folder;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

  @Value("${cloud.aws.s3.bucket-name}")
  private String bucket;

  private final AmazonS3Client amazonS3Client;

  @Override
  public void uploadFile(String fileName, S3Folder s3Folder, MultipartFile multipartFile) {

    // S3 폴더 파라미터 확인
    if (s3Folder == null) {
      throw new RuntimeException();
    }

    // 파일이 비어있는지 확인
    if (multipartFile == null || multipartFile.isEmpty()) {
      throw new RuntimeException();
    }

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(multipartFile.getContentType());
    metadata.setContentLength(multipartFile.getSize());

    final String bucketName = bucket + "/" + s3Folder.getFolderName();

    try {
      amazonS3Client.putObject(bucketName, fileName, multipartFile.getInputStream(), metadata);
    } catch (IOException e) {
      log.error("Failed to upload file to S3", e);
      throw new RuntimeException();
    }
  }

  @Override
  public void removeFile(String fileName, S3Folder s3Folder) {

    try {
      amazonS3Client.deleteObject(bucket + "/" + s3Folder.getFolderName(), fileName);
    } catch (SdkClientException e) {
      throw new RuntimeException();
    }

  }
}
