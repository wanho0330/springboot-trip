package com.wanho.trip.common.auth;

import com.wanho.trip.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final String secretKey;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1일

    public JwtTokenProvider(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = secretKey;
    }

    public SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public TokenInfo createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        Date now = new Date();
        Date accessExpiration = new Date(now.getTime() + EXPIRATION_TIME);

        String jwt = Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .claim("id", ((AuthUser) authentication.getPrincipal()).getId())
                .issuedAt(now)
                .expiration(accessExpiration)
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();

        return new TokenInfo("Bearer", jwt);
    }

    public Authentication getAuthentication(String jwt) {
        Claims claims = getClaims(jwt);

        String auth = Optional.ofNullable(claims.get("auth", String.class))
                .orElseThrow(() -> new RuntimeException("잘못된 토큰입니다."));
        Long id = Optional.ofNullable(claims.get("id", Long.class))
                .orElseThrow(() -> new RuntimeException("잘못된 토큰입니다."));

        Collection<SimpleGrantedAuthority> authorities = Arrays.stream(auth.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new AuthUser(id, claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {

            if (e instanceof SecurityException) {
                log.debug("[SecurityException] 잘못된 토큰");
                throw new JwtException("[SecurityException] 잘못된 토큰입니다.");
            } else if (e instanceof MalformedJwtException) {
                log.debug("[MalformedJwtException] 잘못된 토큰");
                throw new JwtException("[MalformedJwtException] 잘못된 토큰입니다.");
            } else if (e instanceof ExpiredJwtException) {
                log.debug("[ExpiredJwtException] 토큰 만료");
                throw new JwtException("[ExpiredJwtException] 토큰 만료");
            } else if (e instanceof UnsupportedJwtException) {
                log.debug("[UnsupportedJwtException] 잘못된 형식의 토큰");
                throw new JwtException("[UnsupportedJwtException] 잘못된 형식의 토큰");
            } else if (e instanceof IllegalArgumentException) {
                log.debug("[IllegalArgumentException]");
                throw new JwtException("[IllegalArgumentException]");
            } else {
                log.debug("[토큰검증 오류]" + e.getClass());
                throw new JwtException("[토큰검증 오류] 미처리 토큰 오류");
            }
        }
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }
}