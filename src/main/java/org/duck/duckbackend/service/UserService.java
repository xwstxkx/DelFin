package org.duck.duckbackend.service;

import lombok.RequiredArgsConstructor;
import org.duck.duckbackend.entity.UserEntity;
import org.duck.duckbackend.exceptions.UserIsAlreadyExists;
import org.duck.duckbackend.repository.UserRepository;
import org.duck.duckbackend.util.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }
    public UserEntity create(UserEntity user) throws UserIsAlreadyExists {
        if (repository.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new UserIsAlreadyExists();
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new UserIsAlreadyExists();
        }

        return save(user);
    }

    public UserEntity getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Пользователь с тамим никнеймом не найден"
                ));

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
        save(user);
    }
}
