package com.iforddow.authsession.config;

import com.iforddow.authsession.common.AuthProperties;
import com.iforddow.authsession.entity.Session;
import com.iforddow.authsession.repository.DefaultSessionRepository;
import com.iforddow.authsession.repository.SessionRepository;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;

/**
* A configuration class for Redis session management in the authentication module.
*
* @author IFD
* @since 2025-11-27
* */
@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class AuthRedisSessionConfiguration {

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

    /**
    * A bean definition for SessionRepository using Redis.
    *
    * @param authProperties The authentication properties.
    * @param template The RedisTemplate for Session objects.
    * @param stringRedisTemplate The StringRedisTemplate for string operations.
    * @return A SessionRepository implementation using Redis.
    *
    * @author IFD
    * @since 2025-11-29
    * */
    @Bean
    @ConditionalOnMissingBean(SessionRepository.class)
    public SessionRepository sessionRepository(
            AuthProperties authProperties,
            RedisTemplate<String, Session> template,
            StringRedisTemplate stringRedisTemplate
    ) {
        return new DefaultSessionRepository(authProperties, template, stringRedisTemplate);
    }
}
