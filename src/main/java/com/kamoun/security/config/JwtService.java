package com.kamoun.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Validated
public class JwtService {
    private static  final  String SECRET_KEY="DWEXGZH2J4M5N6P8R9SAUCVDWFYGZH3K4M5P7Q8RATBUCWEXFYH2J3K4N6";
    public String extractUsername(String token){

        return  extractClaim(token, Claims::getSubject);
    }
    private Claims extratAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T  extractClaim(String token , Function<Claims,T> claimsResolver){
        Claims claims = extratAllClaims(token);
        return  claimsResolver.apply(claims);
    }
    public  String generateToken(UserDetails userDetails){
        return  generateToken(new HashMap<>(),userDetails);
    }
    public boolean isTokenValid(String token , @NonNull UserDetails userDetails){
        String username = extractUsername(token);
        return (!isTokenExpired(token) && userDetails.getUsername().equals(username));
    }

    private boolean isTokenExpired(String token) {
        return (token!=null && extractClaim(token,Claims::getExpiration).before(new Date()));
    }

    public String generateToken(Map<String ,Object> extraClaims , UserDetails userDetails){
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 1000))
                .signWith(getSigningkey(), SignatureAlgorithm.HS256);
        return jwtBuilder.compact()
                ;

    }

    private Key getSigningkey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
