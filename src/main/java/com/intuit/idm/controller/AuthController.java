package com.intuit.idm.controller;

import com.intuit.idm.dto.AccountVerificationRequest;
import com.intuit.idm.dto.LoginRequest;
import com.intuit.idm.dto.RegisterRequest;
import com.intuit.idm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/idm/v1/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest user){
        String signature = service.registerUser(user);
        return new ResponseEntity<String>(signature, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        String token = service.authenticate(loginRequest);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody AccountVerificationRequest accountVerificationRequest){
        service.verify(accountVerificationRequest);
        return new ResponseEntity<String>("Account activated!", HttpStatus.OK);
    }

    @PostMapping("/resendverification")
    public ResponseEntity<String> resendVerification(@RequestBody String email){
        service.resendVerification(email);
        return new ResponseEntity<String>("Account activated!", HttpStatus.OK);
    }

}
