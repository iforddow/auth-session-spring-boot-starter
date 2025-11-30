package com.iforddow.authsession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return mock(RedisConnectionFactory.class);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}

