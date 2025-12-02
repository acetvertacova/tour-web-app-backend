package com.tour_web_app.aop.aspect;

import com.tour_web_app.aop.annotation.LogEvent;
import com.tour_web_app.aop.enums.EventType;
import com.tour_web_app.entity.CustomUserDetails;
import com.tour_web_app.entity.SystemLog;
import com.tour_web_app.repository.SystemLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class EventLoggingAspect {
    private final SystemLogRepository systemLogRepository;

    @Pointcut("execution(* com.tour_web_app.aop.JwtAuthenticationEntryPoint.commence(..))")
    public void commenceMethodPointCut() {}

    @After("commenceMethodPointCut()")
    public void logUnauthorizedAccess(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        String description = "Unauthorized access to endpoint: %s %s".formatted(request.getMethod(), request.getRequestURI());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logEventToSystem(description, EventType.UNAUTHORIZED_ACCESS.toString());
    }

    @Around("@annotation(logEvent)")
    public Object log(ProceedingJoinPoint joinPoint, LogEvent logEvent) throws Throwable {
        String description = logEvent.description();
        String eventType = logEvent.eventType().toString();
        Object result = null;
        boolean success = false;

        try {
            result = joinPoint.proceed();
            success = true;

            if(!description.isBlank()) {
                description = description + " - SUCCESS";
            } else {
                description = "SUCCESS";
            }

            logEventToSystem(description, eventType);

            return result;
        } catch (Throwable throwable) {
            eventType = "ERROR_" + eventType;
            String failureMessage = throwable.getMessage() != null ? throwable.getMessage() : "Unexpected error";

            if(!description.isBlank()) {
                description = description + " - FAILED: " + failureMessage;
            } else {
                description = "FAILED: " + failureMessage;
            }

            logEventToSystem(description, eventType);
            throw throwable;
        }
    }

    private void logEventToSystem(String description, String eventType) {
        Long userId = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        String ipAddress = request != null ? request.getRemoteAddr() : "unknown";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserDetails customUserDetails) {
                userId = customUserDetails.getUserId();
            } else if (principal instanceof String && "anonymous user".equals(principal)) {
                userId = null;
            }
        }

        SystemLog log = SystemLog.builder()
                .userId((userId != null) ? userId : 0L)
                .ipAddress(ipAddress)
                .eventType(eventType)
                .eventDescription(description)
                .build();

        systemLogRepository.save(log);
    }
}
