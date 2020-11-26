package com.tietoevry.bookorabackend.security.jwt;

import com.tietoevry.bookorabackend.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * This class has 3 functions:
 * 1. Generates a JWT from username, date, expiration, secret
 * 2. Gets username from JWT
 * 3. Validate a JWT
 */

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;


    /**
     * Generate a JWT with username, issue date, expired date and a JWT secret code.
     *
     * @param authentication A Authentication object
     * @return A JWT string
     */
    public String generateJwtToken(Authentication authentication) {

        //Get UserDetails from Authentication object
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        //Use Jwts.class to build JWT, which use DefaultJwtBuilder.class
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Fetches username from JWT
     *
     * @param token A JWT string
     * @return A username string
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }


    /**
     * Validates a JWT
     *
     * @param authToken A JWT string
     * @return True if the JWT is valid
     */
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
}
