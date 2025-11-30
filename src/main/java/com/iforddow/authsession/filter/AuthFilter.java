package com.iforddow.authsession.filter;

import com.iforddow.authsession.validator.SessionValidator;
import com.iforddow.authsession.entity.Session;
import com.iforddow.authsession.utility.FilterUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

/**
* A filter class that checks for valid sessions in incoming HTTP requests.
* If a valid session is found, it sets the authentication in the security context.
*
* @author IFD
* @since 2025-11-27
* */
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final SessionValidator sessionValidator;
    private final FilterUtility filterUtility;

    /**
     * A filter that intercepts HTTP requests to check for active Session in the Authorization header.
     * If a valid Session is found, it authenticates the account and sets the security context.
     *
     * @param request The HTTP request to filter.
     * @param response The HTTP response to filter.
     * @param filterChain The filter chain to continue processing the request.
     *
     * @throws ServletException If an error occurs during filtering.
     * @throws IOException If an I/O error occurs during filtering.
     *
     * @author IFD
     * @since 2025-06-15
     * */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Get existing token from either cookie or header
        String sessionId = filterUtility.getIncomingSessionId(request);

        // If no existing token, return
        if(sessionId == null || sessionId.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        // Load and validate session
        Session session = sessionValidator.loadSession(sessionId);

        // If session is null or invalid, return unauthorized
        if (!sessionValidator.isValid(session)) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        // Set authentication in security context if not already set
        if(SecurityContextHolder.getContext().getAuthentication() == null) {

            // Set the authentication in the security context
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    session.getAccountId(), null, Collections.emptyList()
            );

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);

    }

}
