package com.credence.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

    @Component
    public class ApiKeyInterceptor implements HandlerInterceptor {

        // Grab the secret key from our properties file
        @Value("${app.api.key}")
        private String expectedApiKey;

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            // 1. Check the incoming request for a custom header named "X-API-KEY"
            String providedApiKey = request.getHeader("X-API-KEY");

            // 2. If the keys match, return true (let them pass!)
            if (expectedApiKey.equals(providedApiKey)) {
                return true;
            }

            // 3. If they don't match (or are missing), reject the request
            System.out.println("🚨 [SECURITY ALERT] Blocked unauthorized API access attempt!");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Error
            response.getWriter().write("ACCESS DENIED: Invalid or missing X-API-KEY.");
            return false;
        }
    }

