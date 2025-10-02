package com.afdevelopment.biblioteca.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Ensures the CSRF token is exposed in a cookie for clients (e.g., SPA) to read and echo back
 * in the X-XSRF-TOKEN header for non-idempotent requests.
 *
 * According to Spring Security behavior, the CookieCsrfTokenRepository writes the cookie when the
 * CsrfToken is accessed. This filter forces token resolution and mirrors it into a cookie named
 * "XSRF-TOKEN" (not HttpOnly) so browsers can read it via JavaScript.
 */
public class CsrfCookieFilter extends OncePerRequestFilter {

    private static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            String token = csrfToken.getToken();
            boolean needsUpdate = true;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (CSRF_COOKIE_NAME.equals(cookie.getName())) {
                        needsUpdate = !token.equals(cookie.getValue());
                        break;
                    }
                }
            }
            if (needsUpdate) {
                Cookie cookie = new Cookie(CSRF_COOKIE_NAME, token);
                cookie.setPath("/");
                cookie.setHttpOnly(false); // must be readable by JS to include in X-XSRF-TOKEN header
                // For production over HTTPS, consider cookie.setSecure(true);
                response.addCookie(cookie);
            }
        }
        filterChain.doFilter(request, response);
    }
}
