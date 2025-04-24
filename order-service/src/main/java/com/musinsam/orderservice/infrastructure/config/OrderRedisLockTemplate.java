package com.musinsam.orderservice.infrastructure.config;

import com.musinsam.orderservice.domain.order.exception.OrderException;
import com.musinsam.orderservice.domain.order.vo.OrderErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRedisLockTemplate {

  private final RedissonClient redissonClient;

  public <T> T executeWithLock(List<UUID> orderItemIds, Supplier<T> action) {
    List<RLock> locks = new ArrayList<>();

    try {
      for (UUID orderItemId : orderItemIds) {
        String lockKey = "product_lock:" + orderItemId;
        RLock lock = redissonClient.getLock(lockKey);

        boolean isLocked = lock.tryLock(5, 10, TimeUnit.SECONDS);
        if (!isLocked) {
          releaseAllLocks(locks);
          throw OrderException.from(OrderErrorCode.ORDER_LOCK_FAILED);
        }

        locks.add(lock);
      }

      return action.get();

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw OrderException.from(OrderErrorCode.ORDER_LOCK_FAILED);
    } finally {
      releaseAllLocks(locks);
    }
  }

  private void releaseAllLocks(List<RLock> locks) {
    locks.forEach(lock -> {
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
    });
  }
}
