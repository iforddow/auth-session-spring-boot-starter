package com.iforddow.authsession.repository;

import com.iforddow.authsession.common.AuthProperties;
import com.iforddow.authsession.entity.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
* A service class dressed as a repository for
* session management using Redis.
*
* @author IFD
* @since 2025-11-29
* */
public record DefaultSessionRepository(AuthProperties authProperties,
                                       RedisTemplate<String, Session> sessionRedisTemplate,
                                       StringRedisTemplate stringRedisTemplate) implements SessionRepository {

    /**
    * A method to find a session by its ID.
    *
    * @param sessionId The ID of the session to be retrieved.
    * @return The session object if found, otherwise null.
    *
    * @author IFD
    * @since 2025-11-29
    * */
    @Override
    public Session find(String sessionId) {
        String key = authProperties.getSessionPrefix() + sessionId;
        return sessionRedisTemplate.opsForValue().get(key);
    }

    /**
    * A method to save a session to Redis.
    *
    * @param session The session object to be saved.
    *
    * @author IFD
    * @since 2025-11-29
    * */
    @Override
    public void save(Session session) {
        String sessionKey = authProperties.getSessionPrefix() + session.getSessionId();
        String userSessionsKey = authProperties.getAccountSessionPrefix() + session.getAccountId().toString();

        // Save session object in Redis
        sessionRedisTemplate.opsForValue().set(sessionKey, session);
        sessionRedisTemplate.expireAt(sessionKey, session.getExpiresAt());

        // Add session ID to the user's set of sessions
        stringRedisTemplate.opsForSet().add(userSessionsKey, session.getSessionId());
    }

    /**
    * A method to delete a session from Redis.
    *
    * @param sessionId The ID of the session to be deleted.
    *
    * @author IFD
    * @since 2025-11-29
    * */
    @Override
    public void delete(String sessionId) {
        String key = authProperties.getSessionPrefix() + sessionId;
        sessionRedisTemplate.delete(key);
        stringRedisTemplate.opsForSet().remove(authProperties.getAccountSessionPrefix() + sessionId);
    }

    /**
    * A method to delete a session from Redis.
    *
    * @param session The session object to be deleted.
    *
    * @author IFD
    * @since 2025-11-29
    * */
    @Override
    public void delete(Session session) {

        String sessionId = session.getSessionId();

        delete(sessionId);

    }

    /**
    * A method to check if a session exists in Redis.
    *
    * @param sessionId The ID of the session to check.
    * @return true if the session exists, false otherwise.
    *
    * @author IFD
    * @since 2025-11-29
    * */
    @Override
    public boolean exists(String sessionId) {
        String key = authProperties.getSessionPrefix() + sessionId;

        return sessionRedisTemplate.hasKey(key);
    }
}
