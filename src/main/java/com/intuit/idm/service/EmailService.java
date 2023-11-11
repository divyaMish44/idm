package com.intuit.idm.service;

import com.intuit.idm.dto.AccountVerification;
import com.intuit.idm.model.User;
import com.intuit.idm.utility.CipherUtil;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
//    private EmailClient emailClient;
    public String sendVerificationEmail(User user){
        AccountVerification accountVerification = AccountVerification.builder().id(user.getId())
                .email(user.getUsername()).timestamp(System.currentTimeMillis()).build();
        String signature = CipherUtil.encryptObject(accountVerification);
        /*
         * Generate a verification url with the signature in query param and send
         * email using an email client; e.g mandrill
         * emailClient.send(user.getEmail(),  emailContent)
         * URL : abc.com/verify?s=<signature>
         */

        return signature;
    }

}
