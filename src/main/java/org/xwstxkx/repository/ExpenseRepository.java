package org.xwstxkx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xwstxkx.entity.ExpenseEntity;
import org.xwstxkx.entity.UserEntity;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
//    Page<ExpenseEntity> findAllByUser(UserEntity user, Pageable pageable);

    List<ExpenseEntity> findAllByUser(UserEntity user);

    ExpenseEntity findByIdAndUser(Long id, UserEntity user);

    void deleteByIdAndUser(Long id, UserEntity user);
}
