package org.xwstxkx.service.security;

import lombok.RequiredArgsConstructor;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectWithThisNameIsAlreadyExists;
import org.xwstxkx.repository.UserRepository;
import org.xwstxkx.util.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String saveUser(UserEntity user) {
        userRepository.save(user);
        return "Пользователь сохранён";
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

    public UserEntity findUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findByUsername(currentPrincipalName);
    }
}
