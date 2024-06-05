package org.xwstxkx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xwstxkx.entity.SavingsBudgetEntity;
import org.xwstxkx.entity.UserEntity;

import java.util.List;

@Repository
public interface SavingsBudgetRepository extends JpaRepository<SavingsBudgetEntity, Long> {

    Page<SavingsBudgetEntity> findAllByUser(UserEntity user, Pageable pageable);

    List<SavingsBudgetEntity> findAllByUser(UserEntity user);

    SavingsBudgetEntity findByIdAndUser(Long id, UserEntity user);

    void deleteByIdAndUser(Long id, UserEntity user);

}