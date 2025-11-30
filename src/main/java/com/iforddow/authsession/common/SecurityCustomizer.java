package com.iforddow.authsession.common;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
* An interface for customizing HttpSecurity configurations.
*
* @author IFD
* @since 2025-11-29
* */
@FunctionalInterface
public interface SecurityCustomizer {
    void customize(HttpSecurity http) throws Exception;
}
