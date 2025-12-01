package com.iforddow.authsession.config;

import com.iforddow.authsession.filter.AuthFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
* A security configuration class that adds an authentication filter
* to the HTTP security configuration.
*
* @author IFD
* @since 2025-11-29
* */
public class AuthSecurityConfiguration
        extends AbstractHttpConfigurer<AuthSecurityConfiguration, HttpSecurity> {

    private final AuthFilter filter;

    public AuthSecurityConfiguration(AuthFilter filter) {
        this.filter = filter;
    }

    /**
    * A method to configure HTTP security by adding
    * the authentication filter before the UsernamePasswordAuthenticationFilter.
    *
    * @author IFD
    * @since 2025-11-29
    * */
    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
