package com.bilin.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * Generate JWT
     * Uses HS256 algorithm with a fixed secret key
     *
     * @param secretKey JWT secret key
     * @param ttlMillis JWT expiration time (milliseconds)
     * @param claims    Information to be set
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // Specify signature algorithm used for signing (header part)
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // Generate JWT expiration time
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // Set JWT body
        JwtBuilder builder = Jwts.builder()
                // Set private claims first (these assign values to builder's claims)
                // Any standard claims set after this will be overwritten
                .setClaims(claims)
                // Set signature algorithm and signing key
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                // Set expiration time
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * Token decryption
     *
     * @param secretKey JWT secret key (Must be kept securely on server side,
     *                  never exposed. If supporting multiple clients,
     *                  consider implementing multiple keys)
     * @param token     Encrypted token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
        // Get DefaultJwtParser
        Claims claims = Jwts.parser()
                // Set signing key
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                // Parse the JWT
                .parseClaimsJws(token).getBody();
        return claims;
    }

}