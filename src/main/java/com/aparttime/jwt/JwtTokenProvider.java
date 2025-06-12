package com.aparttime.jwt;

import static com.aparttime.common.constants.JwtTokenConstants.*;

import com.aparttime.config.properties.JwtProperties;
import com.aparttime.exception.jwt.ExpiredAccessTokenException;
import com.aparttime.exception.jwt.ExpiredRefreshTokenException;
import com.aparttime.exception.jwt.ExpiredSecondaryTokenException;
import com.aparttime.exception.jwt.InvalidSignatureException;
import com.aparttime.exception.jwt.InvalidTokenException;
import com.aparttime.exception.jwt.InvalidTokenTypeException;
import com.aparttime.exception.jwt.MalformedTokenException;
import com.aparttime.exception.jwt.UnsupportedTokenException;
import io.jsonwebtoken.Claims;
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
            jwtProperties.getAccessTokenExpiration(),
            ACCESS_TOKEN
        );
    }

    public String createRefreshToken(Long memberId) {
        return createToken(
            memberId,
            jwtProperties.getRefreshTokenExpiration(),
            REFRESH_TOKEN
        );
    }

    public String createSecondaryToken(Long memberId) {
        return createToken(
            memberId,
            jwtProperties.getSecondaryTokenExpiration(),
            SECONDARY_TOKEN
        );
    }

    private String createToken(
        Long memberId,
        long expiration,
        String tokenType
    ) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
            .setSubject(String.valueOf(memberId))
            .claim(TOKEN_TYPE, tokenType)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    // TODO: validate 로직 리팩터링 필요

    public void validateAccessToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

            String tokenType = claims.get(TOKEN_TYPE, String.class);

            if (!ACCESS_TOKEN.equals(tokenType)) {
                throw new InvalidTokenTypeException();
            }
        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException();
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

    public void validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredRefreshTokenException();
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

    public void validateSecondaryToken(String token) {

        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

            String tokenType = claims.get(TOKEN_TYPE, String.class);

            if (!SECONDARY_TOKEN.equals(tokenType)) {
                throw new InvalidTokenTypeException();
            }
        } catch (ExpiredJwtException e) {
            throw new ExpiredSecondaryTokenException();
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

    public String getTokenType(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get(TOKEN_TYPE, String.class);
    }

}
