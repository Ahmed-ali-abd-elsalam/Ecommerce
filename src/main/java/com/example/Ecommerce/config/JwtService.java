package com.example.Ecommerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;

@Service
public class JwtService {

    private static final String  secretKey = "bdbd737533ce86e72792ebbac7811ae4b188fd50c627a5f21b6fb8732cfdd7b8";

    public String extractUsername(String jwt) {
        return "NO";
    }
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private SecretKey getSigningKey() {
        byte [] key = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }
}
