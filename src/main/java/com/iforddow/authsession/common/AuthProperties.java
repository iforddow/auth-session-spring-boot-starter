package com.iforddow.authsession.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* A properties class for authentication session configuration
* Spring Boot Starter.
*
* @author IFD
* @since 2025-11-29
* */
@ConfigurationProperties(prefix = "auth.session")
public class AuthProperties {

    private boolean enabled = true;

    private String sessionPrefix = "auth:session:";

    private String accountSessionPrefix = "auth:account:sessions:";

    private String cookieName = "session";

    private String redisHost = "localhost";

    private int redisPort = 6379;

    private String redisPassword = "";


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSessionPrefix() {
        return sessionPrefix;
    }

    public void setSessionPrefix(String sessionPrefix) {
        this.sessionPrefix = sessionPrefix;
    }

    public String getAccountSessionPrefix() {
        return accountSessionPrefix;
    }

    public void setAccountSessionPrefix(String accountSessionPrefix) {
        this.accountSessionPrefix = accountSessionPrefix;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }
}