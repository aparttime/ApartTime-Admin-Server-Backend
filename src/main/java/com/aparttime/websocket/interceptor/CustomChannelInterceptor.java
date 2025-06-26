package com.aparttime.websocket.interceptor;

import com.aparttime.jwt.JwtTokenProvider;
import com.aparttime.websocket.principal.StompPrincipal;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authorization = accessor.getFirstNativeHeader("Authorization");

            if (authorization != null && authorization.startsWith("Bearer ")) {
                String accessToken = authorization.substring(7);

                log.info(">>> CustomChannelInterceptor preSend() accessToken: {}", accessToken);

                try {
                    jwtTokenProvider.validateAccessToken(accessToken);
                    String memberId = jwtTokenProvider.getMemberId(accessToken);
                    accessor.setUser(new StompPrincipal(Long.parseLong(memberId)));
                } catch (JwtException e) {
                    // TODO: STOMP 메시징 프로토콜 기반 예외 처리 핸들러 도입
                    throw e;
                }
            }
        }

        return message;
    }
}
