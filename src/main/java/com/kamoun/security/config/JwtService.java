package com.kamoun.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
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

    private byte[] getSigningkey() {
        return  null;
    }
}
