package com.aparttime.websocket.interceptor;

import static com.aparttime.common.constants.WebSocketConstants.*;

import com.aparttime.exception.jwt.EmptySecondaryTokenException;
import com.aparttime.jwt.JwtTokenProvider;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean beforeHandshake(
        ServerHttpRequest request,
        ServerHttpResponse response,
        WebSocketHandler wsHandler,
        Map<String, Object> attributes
    ) throws Exception {

        URI uri = request.getURI();
        String query = uri.getQuery();

        log.info(">>> CustomHandshakeInterceptor beforeHandshake() URI: {}", uri);

        String secondaryToken = extractSecondaryToken(query);

        if (secondaryToken == null || secondaryToken.isBlank()) {
            throw new EmptySecondaryTokenException();
        }

        jwtTokenProvider.validateSecondaryToken(secondaryToken);

        return true;
    }

    @Override
    public void afterHandshake(
        ServerHttpRequest request,
        ServerHttpResponse response,
        WebSocketHandler wsHandler,
        Exception exception
    ) {

    }

    private String extractSecondaryToken(
        String query
    ) {
        if (query == null || query.isBlank()) {
            return null;
        }

        for (String parameter : query.split(QUERY_SEPARATOR)) {
            String[] pair = parameter.split(KEY_VALUE_SEPARATOR);
            if (pair.length == PAIR_SIZE && pair[KEY_INDEX].equals(PARAM_SECONDARY_TOKEN)) {
                return pair[VALUE_INDEX];
            }
        }

        return null;
    }
}
