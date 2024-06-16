package org.xwstxkx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xwstxkx.entity.DebtEntity;
import org.xwstxkx.entity.UserEntity;

import java.util.List;

@Repository
public interface DebtsRepository extends JpaRepository<DebtEntity, Long> {
//    Page<DebtEntity> findAllByUser(UserEntity user, Pageable pageable);

    List<DebtEntity> findAllByUser(UserEntity user);

    DebtEntity findByIdAndUser(Long id, UserEntity user);

    void deleteByIdAndUser(Long id, UserEntity user);
}
