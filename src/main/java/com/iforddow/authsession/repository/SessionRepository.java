package com.iforddow.authsession.repository;

import com.iforddow.authsession.entity.Session;

import java.util.List;
import java.util.UUID;

/**
* A repository interface for session management.
*
* @author IFD
* @since 2025-11-29
* */
public interface SessionRepository {
    Session findById(String sessionId);

    void save(Session session);

    void delete(String sessionId);

    void delete(Session session);

    boolean exists(String sessionId);

    List<Session> findAllByAccountId(UUID accountId);
}