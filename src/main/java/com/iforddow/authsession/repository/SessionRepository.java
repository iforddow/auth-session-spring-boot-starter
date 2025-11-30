package com.iforddow.authsession.repository;

import com.iforddow.authsession.entity.Session;

/**
* A repository interface for session management.
*
* @author IFD
* @since 2025-11-29
* */
public interface SessionRepository {
    Session find(String sessionId);

    void save(Session session);

    void delete(String sessionId);

    void delete(Session session);

    boolean exists(String sessionId);
}
