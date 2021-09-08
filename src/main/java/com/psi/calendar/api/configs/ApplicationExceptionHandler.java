package com.psi.calendar.api.configs;

import com.psi.calendar.api.exceptions.InvalidCredentialsException;
import com.psi.calendar.api.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(
            value = HttpStatus.UNAUTHORIZED,
            reason = "Invalid credentials"
    )
    @ExceptionHandler(InvalidCredentialsException.class)
    public void handleException(InvalidCredentialsException e) {
    }

    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "Couldn't find user data with given username"
    )
    @ExceptionHandler(UserNotFoundException.class)
    public void handleException(UserNotFoundException e) {
    }
}
