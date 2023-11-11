package com.intuit.idm.controller;

import com.intuit.idm.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/idm/v1/users")
public class UserController {

    @GetMapping
    public ResponseEntity getUserProfile(Principal principalUser){
        var user = (User) ((UsernamePasswordAuthenticationToken) principalUser).getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
