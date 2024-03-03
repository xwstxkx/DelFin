package org.duck.duckbackend.repository;

import org.duck.duckbackend.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {
    BudgetEntity findByName(String name);
}
