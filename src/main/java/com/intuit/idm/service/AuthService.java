package com.intuit.idm.service;

import com.intuit.idm.jwt.JwtService;
import com.intuit.idm.dto.LoginRequest;
import com.intuit.idm.dto.AccountVerification;
import com.intuit.idm.dto.AccountVerificationRequest;
import com.intuit.idm.dto.RegisterRequest;
import com.intuit.idm.model.User;
import com.intuit.idm.respository.UserRepository;
import com.intuit.idm.utility.CipherUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final static Long VERIFICATION_TIME_LIMIT = 28800000L;

    @Autowired
    UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    @Autowired
    private EmailService emailService;

    public String registerUser(RegisterRequest request) {
        var user = userRepository.findByEmail(request.getEmail());
        System.out.println("-----------user " + user);
        if(!user.isEmpty()){
            throw new RuntimeException("User already exists");
        }
        User createdUser = User.builder().id(UUID.randomUUID().toString())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .status(User.UserStatus.INACTIVE).build();
        userRepository.save(createdUser);
        //encrypt id + email + timestamp with a secrect key and send it to user for verification
        String signature = emailService.sendVerificationEmail(createdUser);
        return signature;
    }

    public void verify(AccountVerificationRequest accountVerificationRequest){
        AccountVerification accountVerification = CipherUtil.decryptObject(
                accountVerificationRequest.getSignature());
        if(System.currentTimeMillis() - accountVerification.getTimestamp() > VERIFICATION_TIME_LIMIT) {
            throw new RuntimeException("Verification link expired!");
        }
        var user = userRepository.findByEmail(accountVerification.getEmail());
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        user.get().setStatus(User.UserStatus.ACTIVE);
        userRepository.save(user.get());
    }

    public String authenticate(LoginRequest loginRequest) throws UsernameNotFoundException{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " +
                        loginRequest.getEmail()));
        if(user.getStatus() != User.UserStatus.ACTIVE){
            throw new RuntimeException("User not verified! Please verify.");
        }
        String token = jwtService.generateToken(user);
        return token;
    }

    public void resendVerification(String email) {
        var user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new RuntimeException("Verification email not sent!");
        }
        emailService.sendVerificationEmail(user.get());
    }
}
