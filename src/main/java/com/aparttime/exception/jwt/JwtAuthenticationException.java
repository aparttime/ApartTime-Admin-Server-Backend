package com.aparttime.exception.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public static JwtAuthenticationException expired() {
        return new JwtAuthenticationException(
            new AccessTokenExpiredException()
        );
    }

    public static JwtAuthenticationException invalidSignature() {
        return new JwtAuthenticationException(
            new InvalidSignatureException()
        );
    }

    public static JwtAuthenticationException malformed() {
        return new JwtAuthenticationException(
            new MalformedTokenException()
        );
    }

    public static JwtAuthenticationException unsupported() {
        return new JwtAuthenticationException(
            new UnsupportedTokenException()
        );
    }

    public static JwtAuthenticationException invalid() {
        return new JwtAuthenticationException(
            new InvalidTokenException()
        );
    }
}