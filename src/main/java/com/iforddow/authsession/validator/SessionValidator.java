package com.iforddow.authsession.validator;

import com.iforddow.authsession.common.SessionProperties;
import com.iforddow.authsession.entity.Session;
import com.iforddow.authsession.utility.SessionHashUtility;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Instant;

/**
 * A session validator class that loads redis, and provides
 * methods to validate sessions.
 *
 * @author IFD
 * @since 2025-11-27
 *
 */
public record SessionValidator(RedisTemplate<String, Session> sessionRedisTemplate, SessionProperties sessionProperties, SessionHashUtility sessionHashUtility) {

    /**
     * A method to load a session from the repository.
     *
     * @param sessionId The ID of the session to load.
     * @return The loaded session object.
     * @author IFD
     * @since 2025-11-29
     *
     */
    public Session loadSession(String sessionId) {
        String key = sessionProperties.getSessionPrefix() + sessionHashUtility.hmacSha256(sessionId);
        return sessionRedisTemplate.opsForValue().get(key);
    }

    /**
     * A method to validate a session.
     *
     * @param session The session to validate.
     * @return true if the session is valid, false otherwise.
     * @author IFD
     * @since 2025-11-27
     *
     */
    public boolean isValid(Session session) {
        if (session == null) {
            return false;
        }

        Instant now = Instant.now();

        // If either expiration is null, consider invalid
        if (session.getHardExpiration() == null || session.getExpiresAt() == null) {
            return false;
        }

        // Check hard expiration first
        if (now.isAfter(session.getHardExpiration())) {
            return false;
        }

        // Then check regular expiration
        return !now.isAfter(session.getExpiresAt());
    }
}
