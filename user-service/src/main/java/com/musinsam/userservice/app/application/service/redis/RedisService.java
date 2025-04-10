package com.musinsam.userservice.app.application.service.redis;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@RequiredArgsConstructor
@Service
public class RedisService {

  private final RedissonClient redissonClient;

  public void saveRefreshToken(Long userId, String refreshToken, long ttlMillis) {
    String key = "RT:" + userId;
    RBucket<String> bucket = redissonClient.getBucket(key);
    bucket.set(refreshToken, Duration.ofMillis(ttlMillis));
  }

  public Optional<String> getRefreshToken(Long userId) {
    String key = "RT:" + userId;
    RBucket<String> bucket = redissonClient.getBucket(key);
    return Optional.ofNullable(bucket.get());
  }

  public void deleteRefreshToken(Long userId) {
    redissonClient.getBucket("RT:" + userId).delete();
  }
}