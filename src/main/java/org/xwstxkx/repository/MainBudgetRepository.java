package org.xwstxkx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xwstxkx.entity.MainBudgetEntity;
import org.xwstxkx.entity.UserEntity;

@Repository
public interface MainBudgetRepository extends JpaRepository<MainBudgetEntity, Long> {

    MainBudgetEntity findByUser(UserEntity user);

}