package com.intuit.idm.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {
    String email;
    String password;
}
