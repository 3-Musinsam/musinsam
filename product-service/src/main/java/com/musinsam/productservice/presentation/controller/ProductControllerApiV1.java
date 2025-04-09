package com.musinsam.productservice.presentation.controller;

import com.musinsam.common.response.ApiResponse;
import com.musinsam.productservice.application.dto.request.ReqProductCreateDtoApiV1;
import com.musinsam.productservice.application.dto.request.ReqProductUpdateDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetListDtoApiV1;
import com.musinsam.productservice.application.dto.response.ResProductGetStockDtoApiV1;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
public class ProductControllerApiV1 {

  /**
   * 상품 등록
   */
  @PostMapping
  public ResponseEntity<ApiResponse<Void>> createProduct(
      @RequestBody ReqProductCreateDtoApiV1 dto) {
    return ResponseEntity.ok().build();
  }

  /**
   * 단일 상품 조회
   */
  @GetMapping
  public ResponseEntity<ApiResponse<ResProductGetDtoApiV1>> getProduct() {
    return ResponseEntity.ok().body(null);
  }

  /**
   * 상품 목록 조회
   */
  @GetMapping("/{product_id}")
  public ResponseEntity<ApiResponse<List<ResProductGetListDtoApiV1>>> getProductList(
      @PathVariable("product_id") UUID id) {
    return ResponseEntity.ok().body(null);
  }

  /**
   * 상품 수정
   */
  @PutMapping("/{product_id}")
  public ResponseEntity<ApiResponse<Void>> updateProduct(
      @PathVariable("product_id") UUID id,
      @RequestBody ReqProductUpdateDtoApiV1 dto) {
    return ResponseEntity.ok().build();
  }

  /**
   * 상품 삭제
   */
  @DeleteMapping("/{product_id}")
  public ResponseEntity<ApiResponse<Void>> deleteProduct(
      @PathVariable("product_id") UUID id) {
    return ResponseEntity.ok().build();
  }

  /**
   * 재고 조회
   */
  @GetMapping("/{product_id}/stock")
  public ResponseEntity<ApiResponse<ResProductGetStockDtoApiV1>> getProductStock(
      @PathVariable("product_id") UUID id) {
    return ResponseEntity.ok().body(null);
  }

  /**
   * 재고 수정
   */
  @PatchMapping("/{product_id}/stock")
  public ResponseEntity<ApiResponse<Void>> updateProductStock(
      @PathVariable("product_id") UUID id) {
    return ResponseEntity.ok().build();
  }
}
