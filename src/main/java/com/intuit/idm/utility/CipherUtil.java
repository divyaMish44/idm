package com.intuit.idm.utility;

import com.intuit.idm.dto.AccountVerification;
import org.springframework.stereotype.Component;

@Component
public class CipherUtil {

    private static final String AES_ALGORITHM = "AES";

    public static String encryptObject(AccountVerification accountVerification){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(accountVerification.getEmail()).append("#").append(accountVerification.getId())
                .append("#").append(accountVerification.getTimestamp());
        return stringBuilder.toString();
    }

    public static AccountVerification decryptObject(String encryptedText){
        String[] str = encryptedText.split("#");
        AccountVerification accountVerification = new AccountVerification(str[0],str[1],Long.valueOf(str[2]));
        return accountVerification;
    }
}
