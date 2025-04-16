package com.musinsam.productservice.infrastructure.image.service;

import com.musinsam.productservice.domain.product.entity.ProductEntity;
import com.musinsam.productservice.domain.product.entity.ProductImageEntity;
import com.musinsam.productservice.domain.product.repository.ProductImageRepository;
import com.musinsam.productservice.domain.product.repository.ProductRepository;
import com.musinsam.productservice.infrastructure.image.S3Folder;
import io.micrometer.common.util.StringUtils;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final S3Service s3Service;
  private final ProductImageRepository productImageRepository;
  private final ProductRepository productRepository;

  @Value("${file.image-extension}")
  private String imageExtension;

  @Override
  @Transactional
  public void saveImageFile(S3Folder s3Folder, MultipartFile file, UUID id) {

    // S3 폴더 파라미터 확인
    if (s3Folder == null) {
      throw new RuntimeException();
    }

    // 파일이 비어있는지 확인
    if (file == null || file.isEmpty()) {
      throw new RuntimeException();
    }

    // 파일 확장자 확인
    final String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
    if (StringUtils.isBlank(fileExtension) || !imageExtension.contains(fileExtension)) {
      throw new RuntimeException();
    }

    // 파일 이름 생성
    final String fileName = UUID.randomUUID() + "." + fileExtension;

    // S3에 업로드
    s3Service.uploadFile(fileName, s3Folder, file);

    if (s3Folder == S3Folder.PRODUCT) {
      saveProductImageInfo(s3Folder, file, id, fileName);
    }
  }

  @Override
  @Transactional
  public void saveProductImageInfo(S3Folder s3Folder, MultipartFile file, UUID id,
      String fileName) {

    ProductEntity product = productRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> new RuntimeException());

    ProductImageEntity productImage = ProductImageEntity.builder()
        .originFileName(file.getOriginalFilename())
        .imageUrl(fileName)
        .fileSize(file.getSize())
        .s3Folder(s3Folder)
        .product(product)
        .build();

    productImageRepository.save(productImage);

  }
}
