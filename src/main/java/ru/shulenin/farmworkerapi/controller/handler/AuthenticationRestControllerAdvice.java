package ru.shulenin.farmworkerapi.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice(basePackages = "ru.shulenin.farmworkerapi.controller")
@Slf4j
public class AuthenticationRestControllerAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    public void handle(String email) {
        log.warn(String.format("AuthRestController.signIn: entity with username=%s not found", email));
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

}
