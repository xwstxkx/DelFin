package org.xwstxkx.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.entity.TransactionEntity;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    Page<TransactionEntity> findAllByBudget(BudgetEntity budgetEntity, Pageable pageable);
    List<TransactionEntity> findAllByBudget(BudgetEntity budgetEntity);
    List<TransactionEntity> findAllByCategory(String category);
}
