package com.example.USLTEST.service.impl;

import com.example.USLTEST.domain.entity.UserEntity;
import com.example.USLTEST.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    @Value("${bookingsystem.app.jwtsecret}")
    private String securityKey;

    private <T> T extractClaim (String token, Function<Claims, T> claimsResolvers){
        final Claims claims  = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }



    private Key getSigninKey(){
        byte [] key = Decoders.BASE64.decode(securityKey);
        return Keys.hmacShaKeyFor(key);
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token,Claims ::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 120))
                .signWith(getSigninKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


    @Override
    public Long getPassengerId() {
        UserEntity user = (UserEntity) SecurityContextHolder
                .getContext().
                getAuthentication()
                .getPrincipal();

        return user.getUserId();
    }

}
