package com.intuit.idm.controller;

import com.intuit.idm.model.User;
import com.intuit.idm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/idm/v1/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest user){
        service.addUser(user);
        return new ResponseEntity<String>("success !", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        String token = service.authenticate(loginRequest);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
}
