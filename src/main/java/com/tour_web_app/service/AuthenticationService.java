package com.tour_web_app.service;

import com.tour_web_app.dto.request.AuthenticationRequest;
import com.tour_web_app.dto.response.AuthenticationResponse;
import com.tour_web_app.exception.AuthFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(final AuthenticationRequest request) {
        try {
            //object contains the input info(username, password) for auth
            UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken
                    .unauthenticated(request.username(), request.password());

            //checks if input === data from db
            Authentication authentication = authenticationManager.authenticate(authToken);

            //if success returns token
            String token = jwtService.generateToken(authentication);
            return new AuthenticationResponse(token);
        } catch (BadCredentialsException ex) {
            throw new AuthFailedException("Bad credentials");
        }
    }
}