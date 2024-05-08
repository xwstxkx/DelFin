package org.xwstxkx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.entity.UserEntity;

import java.util.List;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {

    BudgetEntity findByTitle(String title);
    BudgetEntity findByTitleAndUser(String title, UserEntity user);
    Page<BudgetEntity> findAllByUser(UserEntity user, Pageable pageable);
    List<BudgetEntity> findAllByUser(UserEntity user);

}
