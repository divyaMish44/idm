package com.intuit.idm.service;

import com.intuit.idm.config.JwtService;
import com.intuit.idm.controller.LoginRequest;
import com.intuit.idm.controller.RegisterRequest;
import com.intuit.idm.model.User;
import com.intuit.idm.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public void addUser(RegisterRequest request) {
        User user = User.builder().id(UUID.randomUUID().toString())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .status(User.UserStatus.INACTIVE).build();
        userRepository.save(user);
    }

    public String authenticate(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow();

        String token = jwtService.generateToken(user);
        return token;
    }
}
