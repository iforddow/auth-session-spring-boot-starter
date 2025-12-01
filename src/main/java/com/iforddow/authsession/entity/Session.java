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

    private String sessionId;
    private UUID accountId;
    private Instant createdAt;
    private String ip;
    private String userAgent;
    private Instant expiresAt;
    private Instant hardExpiration;

    public Session(String sessionId, UUID accountId, Instant createdAt, String ip, String userAgent, Instant expiresAt, Instant hardExpiration) {
        this.sessionId = sessionId;
        this.accountId = accountId;
        this.createdAt = createdAt;
        this.ip = ip;
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

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
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
        private String ip;
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

        public Builder ip(String ip) {
            this.ip = ip;
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
            return new Session(sessionId, accountId, createdAt, ip, userAgent, expiresAt, hardExpiration);
        }
    }
}

