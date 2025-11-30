package com.iforddow.authsession.validator;

import com.iforddow.authsession.entity.Session;
import com.iforddow.authsession.repository.SessionRepository;

import java.time.Instant;

/**
 * A session validator class that loads redis, and provides
 * methods to validate sessions.
 *
 * @author IFD
 * @since 2025-11-27
 *
 */
public record SessionValidator(SessionRepository sessionRepository) {

    /**
    * A method to load a session from the repository.
    *
    * @param sessionId The ID of the session to load.
    * @return The loaded session object.
    *
    * @author IFD
    * @since 2025-11-29
    * */
    public Session loadSession(String sessionId) {
        return sessionRepository.find(sessionId);
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
