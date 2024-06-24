package org.xwstxkx.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestUserRepository extends JpaRepository<RestUser, Long> {
    RestUser findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String username);
}
