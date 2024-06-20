package org.xwstxkx.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xwstxkx.entity.AppUser;

import java.util.Optional;

public interface AppUserDao extends JpaRepository<AppUser, Long> {
    AppUser findByTelegramUserId(Long id);
    Optional<AppUser> findByEmail(String email);
}
