package org.xwstxkx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xwstxkx.entity.MainBudgetEntity;
import org.xwstxkx.entity.UserEntity;

import java.util.List;

@Repository
public interface MainBudgetRepository extends JpaRepository<MainBudgetEntity, Long> {

    Page<MainBudgetEntity> findAllByUser(UserEntity user, Pageable pageable);

    List<MainBudgetEntity> findAllByUser(UserEntity user);

    MainBudgetEntity findByIdAndUser(Long id, UserEntity user);

    void deleteByIdAndUser(Long id, UserEntity user);

}