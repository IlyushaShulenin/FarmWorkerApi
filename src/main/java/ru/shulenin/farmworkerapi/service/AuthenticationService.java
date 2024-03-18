package ru.shulenin.farmworkerapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.shulenin.farmworkerapi.dto.JwtAuthenticationResponse;
import ru.shulenin.farmworkerapi.dto.SignInRequest;

/**
 * Сервис для аутентификации
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final WorkerService workerService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) throws UsernameNotFoundException {

            var email = request.getEmail();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    email,
                    request.getPassword()
            ));
            log.info(String.format("User %s authenticated", email));

            var worker = workerService
                    .loadUserByUsername(email);

            var jwt = jwtService.generateToken(worker);
            log.info(String.format("Token for %s created", email));

            return new JwtAuthenticationResponse(jwt);

    }
}
