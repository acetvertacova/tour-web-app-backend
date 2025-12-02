package com.tour_web_app.service;

import com.tour_web_app.entity.Role;
import com.tour_web_app.entity.User;
import com.tour_web_app.exception.CreateFailedException;
import com.tour_web_app.repository.RoleRepository;
import com.tour_web_app.repository.UserRepository;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public User registerUser(User newUser) {
        if (userRepository.existsByUsername(newUser.getUsername()) || userRepository.existsByEmail(newUser.getEmail())) {
            throw new CreateFailedException("Username or Email already exists");
        }

        User user = User.builder()
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword())).build();

        Role roles = roleRepository.findByName("USER").isPresent() ? roleRepository.findByName("USER").get() : null;
        user.setRoles(Collections.singletonList(roles));

        return userRepository.save(user);
    }
}
