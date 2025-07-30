package com.afdevelopment.biblioteca.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingFilter.class);

    private final Map<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    @Value("${security.rate-limit.max-requests:10}")
    private int maxRequests;

    @Value("${security.rate-limit.window-minutes:1}")
    private int windowMinutes;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {

        // Only apply rate limiting to authentication endpoints
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/auth/login") || requestURI.startsWith("/auth/refreshtoken")) {
            String clientIp = getClientIP(request);

            if (isRateLimited(clientIp)) {
                logger.warn("Rate limit exceeded for IP: {}", clientIp);
                response.setStatus(429); // 429 Too Many Requests
                response.getWriter().write("Rate limit exceeded. Please try again later.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isRateLimited(String clientIp) {
        long currentTime = System.currentTimeMillis();
        long windowMillis = TimeUnit.MINUTES.toMillis(windowMinutes);

        RequestCounter counter = requestCounts.computeIfAbsent(clientIp, k -> new RequestCounter());

        // Reset counter if window has expired
        if (currentTime - counter.getLastRequestTime() > windowMillis) {
            counter.reset(currentTime);
        }

        // Increment counter
        counter.incrementCount();
        counter.setLastRequestTime(currentTime);

        // Check if rate limit is exceeded
        return counter.getCount() > maxRequests;
    }

    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private static class RequestCounter {
        private int count;
        private long lastRequestTime;

        public RequestCounter() {
            this.count = 0;
            this.lastRequestTime = System.currentTimeMillis();
        }

        public void reset(long currentTime) {
            this.count = 0;
            this.lastRequestTime = currentTime;
        }

        public void incrementCount() {
            this.count++;
        }

        public int getCount() {
            return count;
        }

        public long getLastRequestTime() {
            return lastRequestTime;
        }

        public void setLastRequestTime(long lastRequestTime) {
            this.lastRequestTime = lastRequestTime;
        }
    }
}
