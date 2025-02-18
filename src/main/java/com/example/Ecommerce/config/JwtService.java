package com.example.Ecommerce.config;

import com.example.Ecommerce.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String  secretKey = "bdbd737533ce86e72792ebbac7811ae4b188fd50c627a5f21b6fb8732cfdd7b8";


    public String extractUsername(String jwt) {
        return  extractClaim(jwt,Claims::getSubject);
    }

    public <T>T extractClaim(String token ,Function<Claims,T> claimsResolver){
         final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public String generateToken(UserDetails userDetails){
        return generateToken(userDetails, new HashMap<>());
    }


    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims){
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +1000*60*60*24))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(UserDetails userDetails,String token) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    public boolean isTokenExpired(String token){
        Date expDate =extractClaim(token,Claims::getExpiration);
        return expDate.before(new Date(System.currentTimeMillis()));
    }

    private SecretKey getSigningKey() {
        byte [] key = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }
}
