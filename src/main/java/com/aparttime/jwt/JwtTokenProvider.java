package com.aparttime.jwt;

import com.aparttime.config.properties.JwtProperties;
import com.aparttime.exception.jwt.AccessTokenExpiredException;
import com.aparttime.exception.jwt.InvalidSignatureException;
import com.aparttime.exception.jwt.InvalidTokenException;
import com.aparttime.exception.jwt.MalformedTokenException;
import com.aparttime.exception.jwt.UnsupportedTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private final JwtProperties jwtProperties;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long memberId) {
        return createToken(
            memberId,
            jwtProperties.getAccessTokenExpiration()
        );
    }

    public String createRefreshToken(Long memberId) {
        return createToken(
            memberId,
            jwtProperties.getRefreshTokenExpiration()
        );
    }

    private String createToken(
        Long memberId,
        long expiration
    ) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
            .setSubject(String.valueOf(memberId))
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new AccessTokenExpiredException();
        } catch (SignatureException e) {
            throw new InvalidSignatureException();
        } catch (MalformedJwtException e) {
            throw new MalformedTokenException();
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedTokenException();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    public String getMemberId(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public Authentication getAuthentication(String token) {
        String memberId = getMemberId(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
