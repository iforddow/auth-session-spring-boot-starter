package com.iforddow.authsession.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * A class representing a user session.
 * It includes details such as session ID, account ID, creation time,
 * IP address, user agent, expiration time, and hard expiration time.
 *
 * @author IFD
 * @since 2025-11-09
 * */
public class Session implements Serializable {

    private final String sessionId;
    private final UUID accountId;
    private final Instant createdAt;
    private final String userAgent;
    private Instant expiresAt;
    private Instant hardExpiration;

    public Session(String sessionId, UUID accountId, Instant createdAt, String userAgent, Instant expiresAt, Instant hardExpiration) {
        this.sessionId = sessionId;
        this.accountId = accountId;
        this.createdAt = createdAt;
        this.userAgent = userAgent;
        this.expiresAt = expiresAt;
        this.hardExpiration = hardExpiration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getSessionId() {
        return sessionId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Instant getHardExpiration() {
        return hardExpiration;
    }

    public void setHardExpiration(Instant hardExpiration) {
        this.hardExpiration = hardExpiration;
    }

    public static class Builder {
        private String sessionId;
        private UUID accountId;
        private Instant createdAt;
        private String userAgent;
        private Instant expiresAt;
        private Instant hardExpiration;

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder accountId(UUID accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Builder hardExpiration(Instant hardExpiration) {
            this.hardExpiration = hardExpiration;
            return this;
        }

        public Session build() {
            return new Session(sessionId, accountId, createdAt, userAgent, expiresAt, hardExpiration);
        }
    }
}

