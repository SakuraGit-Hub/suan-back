package com.kousuan.common.util;

import com.kousuan.common.config.JwtConfig;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

/**
 * @author ljx
 * Jwt工具类
 */

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtConfig jwtConfig;

    //生成JWT令牌
    public String generateToken(String userId) {

        SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(key)
                .compact();
    }

    //解析JWT令牌
    public String parseToken(String token) throws JwtException {

        SecretKey key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //从请求头获取token
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
