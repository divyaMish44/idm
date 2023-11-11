package com.intuit.idm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.NoPermissionException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoPermissionException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Map<String, String> noPermission(NoPermissionException e) {
        return createErrorResponse(e, "Don't have permissions");
    }

    private Map<String, String> createErrorResponse(Exception e, String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getClass().getSimpleName());
        errorResponse.put("message", message);
        return errorResponse;
    }
}