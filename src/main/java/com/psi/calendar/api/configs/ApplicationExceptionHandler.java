package com.psi.calendar.api.configs;

import com.psi.calendar.api.exceptions.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(
            value = HttpStatus.FORBIDDEN,
            reason = "Invalid credentials"
    )
    @ExceptionHandler(InvalidCredentialsException.class)
    public void handleException(ServletException e) {
    }
}
