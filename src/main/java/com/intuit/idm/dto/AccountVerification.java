package com.intuit.idm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class AccountVerification implements Serializable {
    String email;
    String id;
    Long timestamp;
}
