package org.xwstxkx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xwstxkx.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByTelegramUserId(Long id);

    Optional<UserEntity> findByEmail(String email);
}
