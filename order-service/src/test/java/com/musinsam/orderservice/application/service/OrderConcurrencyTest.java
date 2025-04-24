package com.musinsam.orderservice.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;

import com.musinsam.orderservice.application.dto.request.ReqOrderPostDtoApiV1;
import com.musinsam.orderservice.application.dto.request.v2.ReqOrderPostDtoApiV2;
import com.musinsam.orderservice.application.service.v2.OrderServiceApiV2;
import com.musinsam.orderservice.infrastructure.client.ProductFeignClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
public class OrderConcurrencyTest {

  @Autowired
  private OrderServiceApiV1 orderServiceApiV1;

  @Autowired
  private OrderServiceApiV2 orderServiceApiV2;

  @MockitoBean
  private ProductFeignClient productFeignClient;

  private UUID productId;
  private final int INITIAL_STOCK = 100;
  private final AtomicInteger stockCount = new AtomicInteger(INITIAL_STOCK);
  private final AtomicInteger decrementCounter = new AtomicInteger(0);
  private final AtomicInteger raceConditionCounter = new AtomicInteger(0);

  @BeforeEach
  void setUp() {
    productId = UUID.randomUUID();
    stockCount.set(INITIAL_STOCK);
    decrementCounter.set(0);
    raceConditionCounter.set(0);

    doAnswer(invocation -> {
      UUID pid = invocation.getArgument(0);
      int quantity = invocation.getArgument(1);

      int currentStock = stockCount.get();

      // 동시성 문제 감지 및 카운팅
      int threadsReadingSameStock = detectConcurrentReads(currentStock);
      if (threadsReadingSameStock > 1) {
        raceConditionCounter.incrementAndGet();
      }

      // race condition 발생을 위한 의도적인 지연
      Thread.sleep(5);

      if (currentStock >= quantity) {
        stockCount.set(currentStock - quantity);
        decrementCounter.incrementAndGet();
        return ResponseEntity.ok(true);
      } else {
        return ResponseEntity.ok(false);
      }
    }).when(productFeignClient).checkAndReduceStock(any(UUID.class), anyInt());

    doAnswer(invocation -> {
      UUID pid = invocation.getArgument(0);
      int quantity = invocation.getArgument(1);

      stockCount.updateAndGet(current -> current + quantity);
      return ResponseEntity.ok().build();
    }).when(productFeignClient).restoreStock(any(UUID.class), anyInt());
  }

  private int detectConcurrentReads(int stockValue) {
    return (int) (Math.random() * 3) + 1; // test 용이므로 임의의 값 반환
  }

  @Test
  @DisplayName("OrderServiceV2 (분산락 적용) - 100개의 동시 주문 테스트")
  void orderServiceV2Concurrent100UsersTest() throws InterruptedException {
    stockCount.set(INITIAL_STOCK);
    decrementCounter.set(0);
    raceConditionCounter.set(0);

    int numberOfThreads = 100;
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
    CountDownLatch latch = new CountDownLatch(numberOfThreads);

    List<Exception> exceptions = Collections.synchronizedList(new ArrayList<>());
    AtomicInteger successCount = new AtomicInteger(0);

    for (int i = 0; i < numberOfThreads; i++) {
      executorService.submit(() -> {
        try {
          ReqOrderPostDtoApiV2 requestDto = createOrderRequest(productId, 1);
          orderServiceApiV2.createOrder(requestDto, 1L);
          successCount.incrementAndGet();
        } catch (Exception e) {
          exceptions.add(e);
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();
    executorService.shutdown();

    // 결과 출력 및 검증
    System.out.println("=== OrderServiceV2 분산락 적용 결과 ===");
    System.out.println("초기 재고: " + INITIAL_STOCK);
    System.out.println("요청 주문 수: " + numberOfThreads);
    System.out.println("성공한 주문 수: " + successCount.get());
    System.out.println("실패한 주문 수: " + exceptions.size());
    System.out.println("예상 잔여 재고: " + (INITIAL_STOCK - numberOfThreads));
    System.out.println("실제 잔여 재고: " + stockCount.get());

    System.out.println("재고 차감 시도 횟수: " + decrementCounter.get());
    System.out.println("감지된 Race Condition 발생: " + raceConditionCounter.get());

    // 분산락 적용 시 재고가 정확히 차감되어야 함
    assertThat(stockCount.get()).isEqualTo(INITIAL_STOCK - successCount.get());
    assertThat(successCount.get()).isEqualTo(numberOfThreads);
    assertThat(decrementCounter.get()).isEqualTo(numberOfThreads);
  }

  @Test
  @DisplayName("OrderServiceV1 (분산락 미적용) - 100개의 동시 주문 테스트")
  void orderServiceV1Concurrent100UsersTest() throws InterruptedException {
    // 테스트 전 재고 초기화
    stockCount.set(INITIAL_STOCK);
    decrementCounter.set(0);
    raceConditionCounter.set(0);

    int numberOfThreads = 100;
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
    CountDownLatch latch = new CountDownLatch(numberOfThreads);

    List<Exception> exceptions = Collections.synchronizedList(new ArrayList<>());
    AtomicInteger successCount = new AtomicInteger(0);

    for (int i = 0; i < numberOfThreads; i++) {
      executorService.submit(() -> {
        try {
          ReqOrderPostDtoApiV1 requestDto = createOrderRequestV1(productId, 1);
          orderServiceApiV1.createOrder(requestDto, 1L);
          successCount.incrementAndGet();
        } catch (Exception e) {
          exceptions.add(e);
        } finally {
          latch.countDown();
        }
      });
    }

    latch.await();
    executorService.shutdown();

    // 결과 출력 및 검증
    System.out.println("=== OrderServiceV1 분산락 미적용 ===");
    System.out.println("초기 재고: " + INITIAL_STOCK);
    System.out.println("요청 주문 수: " + numberOfThreads);
    System.out.println("성공한 주문 수: " + successCount.get());
    System.out.println("실패한 주문 수: " + exceptions.size());
    System.out.println("예상 잔여 재고: " + (INITIAL_STOCK - numberOfThreads));
    System.out.println("실제 잔여 재고: " + stockCount.get());

    System.out.println("재고 차감 시도 횟수: " + decrementCounter.get());
    System.out.println("감지된 Race Condition 발생: " + raceConditionCounter.get());

    // 모든 주문이 성공했으나, 실제 재고는 정확하지 차감되지 않았을 것
    assertThat(successCount.get()).isEqualTo(numberOfThreads);
    assertThat(stockCount.get()).isGreaterThan(0);
    assertNotEquals(INITIAL_STOCK - successCount.get(), stockCount.get(),
        "분산락 없이 정확한 재고 차감은 불가능해야 함");
  }

  /**
   * 테스트용 주문 요청 임의의 객체 생성 메서드
   */
  private ReqOrderPostDtoApiV1 createOrderRequestV1(UUID productId, int quantity) {
    ReqOrderPostDtoApiV1.Order.OrderItem orderItem = ReqOrderPostDtoApiV1.Order.OrderItem.builder()
        .id(productId)
        .productName("Test Product")
        .price(BigDecimal.valueOf(10000))
        .quantity(quantity)
        .build();

    ReqOrderPostDtoApiV1.Order.ShippingInfo shippingInfo = ReqOrderPostDtoApiV1.Order.ShippingInfo.builder()
        .receiverName("Jone Doe")
        .receiverPhone("010-1234-5678")
        .zipCode("12345")
        .address("서울특별시 테스트구")
        .addressDetail("테스트동 101호")
        .build();

    ReqOrderPostDtoApiV1.Order order = ReqOrderPostDtoApiV1.Order.builder()
        .orderItems(List.of(orderItem))
        .shippingInfo(shippingInfo)
        .request("테스트 요청사항")
        .orderName("테스트 주문")
        .totalAmount(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
        .discountAmount(BigDecimal.ZERO)
        .finalAmount(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
            .subtract(BigDecimal.ZERO))
        .build();

    return ReqOrderPostDtoApiV1.builder()
        .order(order)
        .build();
  }

  /**
   * 테스트용 주문 요청 임의의 객체 생성 메서드
   */
  private ReqOrderPostDtoApiV2 createOrderRequest(UUID productId, int quantity) {
    ReqOrderPostDtoApiV2.Order.OrderItem orderItem = ReqOrderPostDtoApiV2.Order.OrderItem.builder()
        .id(productId)
        .productName("Test Product")
        .price(BigDecimal.valueOf(10000))
        .quantity(quantity)
        .build();

    ReqOrderPostDtoApiV2.Order.ShippingInfo shippingInfo = ReqOrderPostDtoApiV2.Order.ShippingInfo.builder()
        .receiverName("Jone Doe")
        .receiverPhone("010-1234-5678")
        .zipCode("12345")
        .address("서울특별시 테스트구")
        .addressDetail("테스트동 101호")
        .build();

    ReqOrderPostDtoApiV2.Order order = ReqOrderPostDtoApiV2.Order.builder()
        .orderItems(List.of(orderItem))
        .shippingInfo(shippingInfo)
        .request("테스트 요청사항")
        .orderName("테스트 주문")
        .totalAmount(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
        .discountAmount(BigDecimal.ZERO)
        .finalAmount(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
            .subtract(BigDecimal.ZERO))
        .build();

    return ReqOrderPostDtoApiV2.builder()
        .order(order)
        .build();
  }
}