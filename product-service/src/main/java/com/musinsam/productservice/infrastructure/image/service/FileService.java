package com.musinsam.productservice.infrastructure.image.service;

import com.musinsam.productservice.infrastructure.image.S3Folder;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  void saveImageFile(S3Folder s3Folder, MultipartFile file, UUID id);

  void saveProductImageInfo(final S3Folder s3Folder,
      final MultipartFile file,
      final UUID id,
      final String fileName);

}
