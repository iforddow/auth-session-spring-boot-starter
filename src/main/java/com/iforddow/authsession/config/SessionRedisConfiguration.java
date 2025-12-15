package com.iforddow.authsession.config;

import com.iforddow.authsession.entity.Session;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;
import org.jspecify.annotations.NonNull;

/**
* A configuration class for Redis session management in the authentication module.
*
* @author IFD
* @since 2025-11-27
* */
@AutoConfiguration
@ConditionalOnClass(RedisTemplate.class)
public class SessionRedisConfiguration {

    /**
    * A bean definition for RedisTemplate to handle Session objects.
    *
    * @param redisConnectionFactory The Redis connection factory.
    * @param objectMapper The ObjectMapper for JSON serialization.
    * @return A RedisTemplate configured for Session objects.
    *
    * @author IFD
    * @since 2025-11-29
    * */
    @Bean
    @ConditionalOnMissingBean(name = "sessionRedisTemplate")
    public RedisTemplate<String, Session> sessionRedisTemplate(
            RedisConnectionFactory redisConnectionFactory,
            ObjectMapper objectMapper
    ) {
        RedisTemplate<String, Session> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        JacksonJsonRedisSerializer<@NonNull Session> serializer =
                new JacksonJsonRedisSerializer<>(objectMapper, Session.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        return template;
    }
}
