package com.musinsam.userservice.app.infrastructure.jwt;

import com.musinsam.userservice.app.domain.auth.jwt.JwtProvider;
import com.musinsam.userservice.app.domain.user.vo.UserRoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProviderImpl implements JwtProvider {

  private final Key accessKey;
  private final Key refreshKey;
  private final SecretKey secretKey;

  @Value("${spring.application.name}")
  private String issuer;

  @Value("${jwt.access.expiration}")
  private Long accessTokenExpiration;

  @Value("${jwt.refresh.expiration}")
  private Long refreshTokenExpiration;

  public JwtProviderImpl(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.accessKey = Keys.hmacShaKeyFor(keyBytes);
    this.refreshKey = Keys.hmacShaKeyFor(keyBytes);
    this.secretKey = Keys.hmacShaKeyFor(keyBytes);
  }

  public String createAccessToken(Long id, UserRoleType role) {
    return Jwts.builder()
        .claim("userId", id)
        .claim("userRole", role)
        .issuer(issuer)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
        .signWith(secretKey)
        .compact();
  }

  public String createRefreshToken(Long id, UserRoleType role) {
    return Jwts.builder()
        .claim("userId", id)
        .claim("userRole", role)
        .issuer(issuer)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
        .signWith(secretKey)
        .compact();
  }

  public Boolean validateToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(secretKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Long getUserIdFromToken(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    return claims.get("userId", Long.class);
  }


  public UserRoleType getUserRoleFromToken(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();

    String role = claims.get("userRole", String.class);

    return UserRoleType.valueOf(role);
  }
}
