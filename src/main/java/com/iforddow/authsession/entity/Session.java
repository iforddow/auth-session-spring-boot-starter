package com.iforddow.authsession.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session implements Serializable {

    private String sessionId;
    private UUID accountId;
    private Instant createdAt;
    private String ip;
    private String userAgent;
    private Instant expiresAt;
    private Instant hardExpiration;

}

