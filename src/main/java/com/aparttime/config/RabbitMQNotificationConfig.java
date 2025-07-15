package com.aparttime.config;

import com.aparttime.config.properties.RabbitMQProperties;
import com.aparttime.notification.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQNotificationConfig {

    private final RabbitMQProperties rabbitMQProperties;

    /**
     * FanoutExchange - 모든 Queue로 메시지를 broadcast
     */
    @Bean
    public FanoutExchange notificationExchange() {
        return new FanoutExchange(
            rabbitMQProperties.exchangeName(),
            true,
            false
        );
    }

    /**
     * 단일 Notification Queue
     */
    @Bean
    public Queue notificationQueue() {
        return new Queue(
            rabbitMQProperties.queueName(),
            true,
            false,
            false
        );
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(
        ConnectionFactory connectionFactory,
        Queue notificationQueue,
        NotificationService notificationService,
        ObjectMapper objectMapper
    ) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(notificationQueue);
        container.setConcurrentConsumers(1);

        container.
    }

}
