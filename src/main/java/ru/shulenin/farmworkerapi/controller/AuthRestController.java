package ru.shulenin.farmworkerapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shulenin.farmworkerapi.dto.JwtAuthenticationResponse;
import ru.shulenin.farmworkerapi.dto.SignInRequest;
import ru.shulenin.farmworkerapi.service.AuthenticationService;

/**
 * Контроллер для аутентификации
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthenticationService authenticationService;

    /**
     * Аутентификация рабочего
     * @param request dto для аутентификации
     * @return JWT токен
     */
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request)
            throws UsernameNotFoundException {
        return authenticationService.signIn(request);
    }
}
