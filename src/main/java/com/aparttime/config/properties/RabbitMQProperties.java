package com.aparttime.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQProperties {

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String queueName;

    public RabbitMQProperties(
        String host,
        int port,
        String username,
        String password,
        String queueName
    ) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.queueName = queueName;
    }
}
