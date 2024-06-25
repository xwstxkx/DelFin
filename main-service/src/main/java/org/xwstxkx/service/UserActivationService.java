package org.xwstxkx.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserActivationService {
    private final UserRepository userRepository;
    public boolean activation(Long userId) throws ObjectNotFound {
        UserEntity user = userRepository.findById(userId).orElseThrow(ObjectNotFound::new);
        if (user != null) {
            user.setIsActive(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
