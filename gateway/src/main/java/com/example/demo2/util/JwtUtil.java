package com.example.demo2.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private static String signKey = "qwerty";
    private static Long expire = 2592000L;

    public static String generateJwt(String code){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", code);
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    public Boolean validateAccessToken(String token, String id) {
        final String extractedId = extractId(token);
        return (extractedId.equals(id) && !isTokenExpired(token));
    }

    public String extractId(String token) {
        String id = parseJWT(token).get("id").toString();
        return id;
    }

    public Date extractExpiration(String token) {
        Date expiration = parseJWT(token).getExpiration();
        return expiration;
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
