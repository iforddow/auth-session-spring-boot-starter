package com.iforddow.authsession.utility;

import com.iforddow.authsession.common.AuthProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;


import java.util.Objects;

/**
 * A utility class for filter-related operations.
 *
 * @author IFD
 * @since 2025-11-29
 *
 */
public record FilterUtility(AuthProperties authProperties) {

    /**
     * A method to normalize a token by removing the "Bearer " prefix if present.
     *
     * @param header The header or token string to normalize.
     * @return The normalized token string.
     * @author IFD
     * @since 2025-11-04
     *
     */
    private String getSessionIdFromHeader(String header) {
        if (header == null) return null;
        String value = header.trim();
        if (value.toLowerCase().startsWith("bearer ")) {
            return value.substring(7).trim();
        }
        return value;
    }

    /**
     * A method to extract the session ID from cookies.
     *
     * @param cookies The array of cookies from the HTTP request.
     * @return The session ID if found, otherwise null.
     * @author IFD
     * @since 2025-11-05
     *
     */
    private String getSessionIdFromCookie(Cookie[] cookies) {

        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(authProperties.getCookieName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    /**
     * A method to ensure that only one token is provided
     * either via cookie or Authorization header. If both or none
     * are provided, an exception is thrown. This helps as
     * mobile apps will send tokens in headers while web apps
     * will use cookies.
     *
     * @param request The HTTP servlet request containing cookies and headers.
     * @return The single token provided.
     * @author IFD
     * @since 2025-11-05
     *
     */
    public String getIncomingSessionId(HttpServletRequest request) {

        // Get cookies and Authorization header
        Cookie[] cookies = request.getCookies();
        String authHeader = request.getHeader("Authorization");

        // Extract session id from Authorization header
        String sessionIdFromHeader = getSessionIdFromHeader(authHeader);

        // Extract session id from cookies
        String sessionIdFromCookie = getSessionIdFromCookie(cookies);

        // If both are provided, check to see if they are the same, if not, throw exception
        if (isNotNullOrEmpty(sessionIdFromHeader) && isNotNullOrEmpty(sessionIdFromCookie)) {
            if (!sessionIdFromHeader.equals(sessionIdFromCookie)) {
                return null;
            }
        }

        // If the cookie session id is present, return it
        if(isNotNullOrEmpty(sessionIdFromCookie)) {
            return sessionIdFromCookie;
        }

        // Otherwise return the header session id
        return sessionIdFromHeader;

    }

    /**
     * A method to check if a string is not null or empty.
     *
     * @param string The string to check.
     * @return true if the string is not null and not empty, false otherwise.
     * @author IFD
     * @since 2025-11-29
     *
     */
    public boolean isNotNullOrEmpty(String string) {
        return string != null && !string.isBlank();
    }

}
