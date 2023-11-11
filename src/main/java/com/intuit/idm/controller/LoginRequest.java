package com.intuit.idm.controller;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {
    String email;
    String password;
}
