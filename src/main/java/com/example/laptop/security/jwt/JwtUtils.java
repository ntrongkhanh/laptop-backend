package com.example.laptop.security.jwt;


import com.example.laptop.db.repository.UserRepository;
import com.example.laptop.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final String AUTHORITIES_KEY = "auth";

    @Autowired
    private UserRepository userRepository;

    @Value("${laptop.app.jwtSecret}")
    private String jwtSecret;

    @Value("${laptop.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Date currentDate = new Date();
        String token = Jwts.builder()
                .setId(userPrincipal.getId().toString())
                .setSubject((userPrincipal.getUsername()))
                .claim(AUTHORITIES_KEY, userPrincipal.getAuthorities())
                .setIssuedAt(currentDate)
                .setExpiration(new Date((currentDate).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        return token;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(parseJwt(token)).getBody().getSubject();
    }

    public Long getUserIdFromJwtToken(String token) {
        return Long.parseLong(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(parseJwt(token)).getBody().getId());
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private String parseJwt(String request) {
        if (StringUtils.hasText(request) && request.startsWith("Bearer ")) {
            return request.substring(7);
        }

        return request;
    }
}