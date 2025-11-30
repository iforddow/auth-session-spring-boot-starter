package com.iforddow.authsession.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* A properties class for authentication session configuration
* Spring Boot Starter.
*
* @author IFD
* @since 2025-11-29
* */
@Data
@ConfigurationProperties(prefix = "auth.session")
public class AuthProperties {

    private boolean enabled = true;

    private String sessionPrefix = "auth:session:";

    private String accountSessionPrefix = "auth:account:sessions:";

    private String cookieName = "session";

    private String redisHost = "localhost";

    private int redisPort = 6379;

    private String redisPassword = "";
}