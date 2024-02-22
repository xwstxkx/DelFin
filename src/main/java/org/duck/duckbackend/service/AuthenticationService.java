package org.duck.duckbackend.service;

import lombok.RequiredArgsConstructor;
import org.duck.duckbackend.entity.UserEntity;
import org.duck.duckbackend.exceptions.UserIsAlreadyExists;
import org.duck.duckbackend.model.JwtResponse;
import org.duck.duckbackend.model.SignInRequest;
import org.duck.duckbackend.model.SignUpRequest;
import org.duck.duckbackend.util.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtResponse signUp(SignUpRequest request) throws UserIsAlreadyExists {

        var user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.createUser(user);

        var jwt = jwtService.generateToken(user);
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
        return new JwtResponse(jwt);
    }
}

