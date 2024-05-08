package org.xwstxkx.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectWithThisNameIsAlreadyExists;
import org.xwstxkx.model.JwtResponse;
import org.xwstxkx.model.SignInRequest;
import org.xwstxkx.model.SignUpRequest;
import org.xwstxkx.util.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final BudgetService budgetService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtResponse signUp(SignUpRequest request) throws ObjectWithThisNameIsAlreadyExists {

        var user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.createUser(user);
        log.info("Пользователь создан");
        budgetService.createBudgetSignup(user);
        log.info("Базовые бюджеты пользователя созданы");

        var jwt = jwtService.generateToken(user);
        log.info("Токен сгенерирован");
        return new JwtResponse(jwt);
    }

    public JwtResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        log.info("Токен сгенерирован");
        return new JwtResponse(jwt);
    } 
}

