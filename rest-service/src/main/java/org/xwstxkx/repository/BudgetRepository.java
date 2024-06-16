package org.xwstxkx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.entity.UserEntity;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {

    Page<BudgetEntity> findAllByUser(UserEntity user, Pageable pageable);
    List<BudgetEntity> findAllByUser(UserEntity user);
    BudgetEntity findByIdAndUser(Long id, UserEntity user);
    void deleteByIdAndUser(Long id, UserEntity user);

}
