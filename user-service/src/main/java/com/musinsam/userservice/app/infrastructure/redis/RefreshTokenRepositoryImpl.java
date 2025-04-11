package com.musinsam.userservice.app.infrastructure.redis;

import com.musinsam.userservice.app.domain.user.repository.token.RefreshTokenRepository;
import java.time.Duration;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Getter
@RequiredArgsConstructor
@Service
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

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