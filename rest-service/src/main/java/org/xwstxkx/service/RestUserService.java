package org.xwstxkx.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.xwstxkx.user.RestUser;
import org.xwstxkx.exceptions.ObjectWithThisNameIsAlreadyExists;
import org.xwstxkx.user.RestUserRepository;
import org.xwstxkx.util.Role;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestUserService {

    private final RestUserRepository restUserRepository;

    public void saveUser(RestUser user) {
        restUserRepository.save(user);
        log.info("Пользователь успешно сохранён!");
    }

    public void createUser(RestUser user) throws ObjectWithThisNameIsAlreadyExists {
        if (restUserRepository.existsByUsername(user.getUsername())
                || restUserRepository.existsByEmail(user.getEmail())
                || user.getUsername().endsWith("tgbot@3")) {
            throw new ObjectWithThisNameIsAlreadyExists();
        }
        saveUser(user);
    }

    public RestUser getByUsername(String username) {
        return restUserRepository.findByUsername(username);
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public RestUser getCurrentUser() {
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
