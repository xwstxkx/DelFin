package org.xwstxkx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xwstxkx.entity.IncomeEntity;
import org.xwstxkx.entity.UserEntity;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
//    Page<IncomeEntity> findAllByUser(UserEntity user, Pageable pageable);

    List<IncomeEntity> findAllByUser(UserEntity user);

    IncomeEntity findByIdAndUser(Long id, UserEntity user);

    void deleteByIdAndUser(Long id, UserEntity user);
}
