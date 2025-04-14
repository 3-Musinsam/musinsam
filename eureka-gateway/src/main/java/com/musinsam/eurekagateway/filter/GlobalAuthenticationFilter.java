package com.musinsam.eurekagateway.filter;


import com.musinsam.eurekagateway.config.AuthWhitelistProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Stream;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j(topic = "JwtAuthenticationFilter")
@RequiredArgsConstructor
@Component
public class GlobalAuthenticationFilter implements GlobalFilter, Ordered {

  private final AuthWhitelistProperties whitelistProperties;
  private final ReactiveStringRedisTemplate redisTemplate;

  private static final AntPathMatcher pathMatcher = new AntPathMatcher();
  private static final String HEADER_USER_ID = "X-USER-ID";
  private static final String HEADER_USER_ROLE = "X-USER-ROLE";

  private SecretKey secretKey;

  @Value("${jwt.secret}")
  private String secretValue;

  @PostConstruct
  public void initSecretKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretValue);
    this.secretKey = Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();

    if (isWhitelisted(path)) {
      return chain.filter(exchange);
    }

    return extractToken(exchange)
        .flatMap(token -> parseClaims(token)
            .flatMap(claims -> isTokenBlacklisted(token)
                .flatMap(isBlacklisted -> {
                  if (isBlacklisted) {
                    return onError(exchange, "Token is blacklisted");
                  }
                  ServerWebExchange mutatedExchange = addHeaders(exchange, claims);
                  return chain.filter(mutatedExchange);
                })))
        .switchIfEmpty(onError(exchange, "Token is missing or invalid"));
  }

  private boolean isWhitelisted(String path) {
    return whitelistProperties.getWhitelist().stream()
        .anyMatch(white -> pathMatcher.match(white, path));
  }

  private Mono<String> extractToken(ServerWebExchange exchange) {
    String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return Mono.empty();
    }
    return Mono.just(authHeader.substring(7));
  }

  private Mono<Claims> parseClaims(String token) {
    try {
      Claims claims = Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();
      return Mono.just(claims);
    } catch (JwtException e) {
      log.warn("[JWT parsing error]: {}", e.getMessage());
      return Mono.empty();
    }
  }

  private ServerWebExchange addHeaders(ServerWebExchange exchange, Claims claims) {
    Long userId = claims.get("userId", Long.class);
    String role = claims.get("userRole", String.class);

    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
        .header(HEADER_USER_ID, String.valueOf(userId))
        .header(HEADER_USER_ROLE, role)
        .build();

    return exchange.mutate().request(mutatedRequest).build();
  }

  private Mono<Void> onError(ServerWebExchange exchange, String message) {
    log.info("[Authentication failed]: {}", message);
    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    return exchange.getResponse().setComplete();
  }

  private Mono<Boolean> isTokenBlacklisted(String token) {
    String key = "BL:" + token;
    return redisTemplate.hasKey(key);
  }

  @Override
  public int getOrder() {
    return -1;
  }
}