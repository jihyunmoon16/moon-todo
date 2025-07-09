package com.moon.todo.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // 원래 비밀키 (환경변수 등에서 로딩 권장)
    private String secretKey = "my-very-secret-key-for-jwt-token-signing-should-be-long-enough";

    private SecretKey signingKey;

    // 토큰 유효 시간 (1시간)
    private final long expirationMs = 1000 * 60 * 60;

    @PostConstruct
    protected void init() {
        // Base64 인코딩 & 키 생성
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * 토큰 생성
     */
    public String createToken(Long userId, String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(String.valueOf(userId))      // subject는 보통 유저 식별자
                .claim("email", email)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 토큰에서 userId 추출
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(signingKey)     // 🔄 최신 API 방식
                .build()
                .parseSignedClaims(token)   // 🔄 parseClaimsJws → parseSignedClaims
                .getPayload();              // 🔄 getBody() → getPayload()

        return Long.parseLong(claims.getSubject());
    }

    /**
     * 토큰 유효성 검사
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token); // 파싱이 되면 유효
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
