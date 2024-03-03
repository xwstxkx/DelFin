package org.duck.duckbackend.service;

import lombok.RequiredArgsConstructor;
import org.duck.duckbackend.entity.UserEntity;
import org.duck.duckbackend.exceptions.UserIsAlreadyExists;
import org.duck.duckbackend.repository.UserRepository;
import org.duck.duckbackend.util.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public void createUser(UserEntity user) throws UserIsAlreadyExists {
        if (userRepository.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new UserIsAlreadyExists();
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserIsAlreadyExists();
        }

        saveUser(user);
    }

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username);

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public UserEntity getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        saveUser(user);
    }

    public UserEntity findUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByUsername(currentPrincipalName);
    }
}
