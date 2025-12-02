package com.tour_web_app.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour_web_app.exception.enums.ErrorType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("errorResponse", "401");
        errorResponse.put("errorDescription", "Unauthorized access.");
        errorResponse.put("impact", "The request cannot be processed without valid credentials.");
        errorResponse.put("errorType", ErrorType.CRITICAL);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
