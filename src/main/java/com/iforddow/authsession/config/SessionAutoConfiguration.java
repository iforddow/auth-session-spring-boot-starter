package com.iforddow.authsession.config;

import com.iforddow.authsession.common.SessionProperties;
import com.iforddow.authsession.entity.Session;
import com.iforddow.authsession.utility.SessionFilterUtility;
import com.iforddow.authsession.utility.SessionHashUtility;
import com.iforddow.authsession.validator.SessionValidator;
import com.iforddow.authsession.filter.SessionFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
* A configuration class for AuthSession auto-configuration.
*
* @author IFD
* @since 2025-11-27
* */
@AutoConfiguration
@AutoConfigureAfter(DataRedisAutoConfiguration.class)
@EnableConfigurationProperties(SessionProperties.class)
@ConditionalOnProperty(prefix = "auth.session", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SessionAutoConfiguration {

    /**
     * Explicitly define the SessionProperties bean.
     * This ensures it is registered in the context even if scanning fails.
     *
     * @return The SessionProperties bean.
     */
    @Bean
    @ConditionalOnMissingBean
    public SessionProperties sessionProperties() {
        return new SessionProperties();
    }

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
    public SessionFilterUtility sessionFilterUtility(SessionProperties sessionProperties) {
        return new SessionFilterUtility(sessionProperties);
    }

    /*
    * A bean that creates a HashUtility using the provided SessionProperties.
    *
    * @return The created HashUtility.
    *
    * @author IFD
    * @since 2025-11-27
    * */
    @Bean
    @ConditionalOnMissingBean
    public SessionHashUtility sessionHashUtility(SessionProperties sessionProperties) {
        return new SessionHashUtility(sessionProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(RedisConnectionFactory.class)
    public SessionValidator sessionValidator(
            RedisTemplate<String, Session> sessionRedisTemplate,
            SessionProperties sessionProperties, SessionHashUtility sessionHashUtility) {
        return new SessionValidator(sessionRedisTemplate, sessionProperties, sessionHashUtility);
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
    @ConditionalOnBean(RedisConnectionFactory.class)
    public SessionFilter sessionFilter(SessionValidator sessionValidator, SessionFilterUtility filterUtility) {
        return new SessionFilter(sessionValidator, filterUtility);
    }

}
