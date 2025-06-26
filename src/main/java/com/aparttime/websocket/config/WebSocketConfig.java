package com.aparttime.websocket.config;

import static com.aparttime.common.constants.WebSocketConstants.*;

import com.aparttime.websocket.interceptor.CustomChannelInterceptor;
import com.aparttime.websocket.interceptor.CustomHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final CustomHandshakeInterceptor customHandshakeInterceptor;
    private final CustomChannelInterceptor customChannelInterceptor;

    @Override
    public void registerStompEndpoints(
        StompEndpointRegistry registry
    ) {
        registry.addEndpoint(WEB_SOCKET_PATH)
            .addInterceptors(customHandshakeInterceptor)
            .setAllowedOriginPatterns("*")
            .withSockJS();
    }

    @Override
    public void configureMessageBroker(
        MessageBrokerRegistry registry
    ) {
        // 클라이언트에서 구독하는 경로 (서버 -> 클라이언트)
        registry.enableSimpleBroker(TOPIC_PREFIX, "/queue");

        // 클라이언트가 메시지를 보낼 경로 (클라이언트 -> 서버)
        registry.setApplicationDestinationPrefixes(PUB_PREFIX);
    }

    @Override
    public void configureClientInboundChannel(
        ChannelRegistration registration
    ) {
        registration.interceptors(customChannelInterceptor);
    }
}
