package com.kamoun.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {
    private static  final  String SECRET_KEY="DWEXGZH2J4M5N6P8R9SAUCVDWFYGZH3K4M5P7Q8RATBUCWEXFYH2J3K4N6";
    public String extractUsername(String jwt){
        return  null;
    }
    private Claims extratAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningkey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
