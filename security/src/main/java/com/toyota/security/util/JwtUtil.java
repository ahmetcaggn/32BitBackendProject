package com.toyota.security.util;

import com.toyota.entity.Employee;
import com.toyota.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Component
public class JwtUtil {

    @Value("${jwt.key}")
    private String SECRET_KEY;


    Long EXPIRATION_TIME = (long) 1000 * 60 * 60 * 24;

    public String generateToken(String username, Set<Role> roleSet) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles",roleSet.toString());
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey())
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        Date expirationDate = extractExpiration(token);
        return username.equals(userDetails.getUsername()) && expirationDate.after(new Date(System.currentTimeMillis()));
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

//    public List<Role> extractRoles(String token){
//        return extractAllClaims(token).get("roles",List.class);
//    }
}
