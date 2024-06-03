package org.xwstxkx.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectWithThisNameIsAlreadyExists;
import org.xwstxkx.repository.UserRepository;
import org.xwstxkx.util.Role;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(UserEntity user) {
        userRepository.save(user);
        log.info("Пользователь успешно сохранён!");
    }

    public void createUser(UserEntity user) throws ObjectWithThisNameIsAlreadyExists {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ObjectWithThisNameIsAlreadyExists();
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ObjectWithThisNameIsAlreadyExists();
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
}
