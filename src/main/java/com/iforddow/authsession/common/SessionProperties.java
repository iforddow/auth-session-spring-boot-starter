package com.iforddow.authsession.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

/**
* A properties class for authentication session configuration
* Spring Boot Starter.
*
* @author IFD
* @since 2025-11-29
* */
@ConfigurationProperties(prefix = "auth.session")
public class SessionProperties {

    private boolean enabled = true;

    @Name("session.prefix")
    private String sessionPrefix = "auth:session:";

    @Name("account.session.prefix")
    private String accountSessionPrefix = "auth:account:sessions:";

    @Name("cookie.name")
    private String cookieName = "session";

    @Name("hash.secret")
    private String hashSecret = "ChangeThis;ToASecureRandomString";

    @Name("hash.algo")
    private String hashAlgo = "HmacSHA256";


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

    public String getHashSecret() {
        return hashSecret;
    }

    public void setHashSecret(String hashSecret) {
        this.hashSecret = hashSecret;
    }

    public String getHashAlgo() {
        return hashAlgo;
    }

    public void setHashAlgo(String hashAlgo) {
        this.hashAlgo = hashAlgo;
    }
}