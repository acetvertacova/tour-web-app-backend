package com.tour_web_app.controller;

import com.tour_web_app.aop.annotation.LogEvent;
import com.tour_web_app.aop.enums.EventType;
import com.tour_web_app.dto.mapper.RegistrationMapper;
import com.tour_web_app.dto.request.AuthenticationRequest;
import com.tour_web_app.dto.request.RegistrationRequest;
import com.tour_web_app.dto.response.AuthenticationResponse;
import com.tour_web_app.dto.response.RegistrationResponse;
import com.tour_web_app.entity.User;
import com.tour_web_app.service.AuthenticationService;
import com.tour_web_app.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserRegistrationService userRegistrationService;
    private final RegistrationMapper registrationMapper;

    @PostMapping("/login")
    @LogEvent(eventType = EventType.LOGIN)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody final AuthenticationRequest request) {
        log.info(request.toString());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    @LogEvent(eventType = EventType.REGISTER)
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody RegistrationRequest request) {
        User registeredUser = userRegistrationService.registerUser(registrationMapper.toUserEntity(request));

        return ResponseEntity.ok(registrationMapper.toRegistrationResponse(registeredUser));
    }
}