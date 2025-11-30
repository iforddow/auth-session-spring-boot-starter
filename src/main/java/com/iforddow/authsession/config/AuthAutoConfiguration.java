package com.iforddow.authsession.config;

import com.iforddow.authsession.common.AuthProperties;
import com.iforddow.authsession.common.SecurityCustomizer;
import com.iforddow.authsession.validator.SessionValidator;
import com.iforddow.authsession.filter.AuthFilter;
import com.iforddow.authsession.repository.SessionRepository;
import com.iforddow.authsession.utility.FilterUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
* A configuration class for AuthSession auto-configuration.
*
* @author IFD
* @since 2025-11-27
* */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(AuthProperties.class)
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "auth.session",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true)
public class AuthAutoConfiguration {

    private final AuthProperties authProperties;

    /**
    * A bean that creates a FilterUtility using the provided AuthProperties.
    *
    * @return The created FilterUtility.
    *
    * @author IFD
    * @since 2025-11-29
    * */
    @Bean
    @ConditionalOnMissingBean
    public FilterUtility filterUtility() {
        return new FilterUtility(authProperties);
    }

    /**
    * A bean that creates a SessionValidator using the provided SessionRepository.
    *
    * @param sessionRepository The SessionRepository to use.
    * @return The created SessionValidator.
    *
    * @author IFD
    * @since 2025-11-27
    * */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(SessionRepository.class)
    public SessionValidator sessionValidator(SessionRepository sessionRepository) {
        return new SessionValidator(sessionRepository);
    }

    /**
    * A bean that creates an AuthFilter using the provided SessionValidator.
    *
    * @param sessionValidator The SessionValidator to use in the AuthFilter.
    * @return The created AuthFilter.
    *
    * @author IFD
    * @since 2025-11-27
    * */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(SessionValidator.class)
    public AuthFilter authFilter(SessionValidator sessionValidator, FilterUtility filterUtility) {
        return new AuthFilter(sessionValidator, filterUtility);
    }

    /**
    * A bean that configures the AuthSecurityConfiguration with the provided AuthFilter.
    *
    * @param authFilter The AuthFilter to add to the filter chain.
    * @return The configured SecurityFilterChain.
    *
    * @author IFD
    * @since 2025-11-27
    * */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(AuthFilter.class)
    public AuthSecurityConfiguration authSecurityConfigurer(AuthFilter authFilter) {
        return new AuthSecurityConfiguration(authFilter);
    }

    /**
    * A bean that customizes the security configuration using AuthSecurityConfiguration.
    *
    * @param authSecurityConfiguration The AuthSecurityConfiguration to apply.
    * @return The SecurityCustomizer that applies the configuration.
    *
    * @author IFD
    * @since 2025-11-29
    * */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(AuthSecurityConfiguration.class)
    public SecurityCustomizer authSecurityCustomizer(AuthSecurityConfiguration authSecurityConfiguration) {
        return http -> http.apply(authSecurityConfiguration);
    }

}
