package org.duck.duckbackend.service;

import lombok.AllArgsConstructor;
import org.duck.duckbackend.entity.BudgetEntity;
import org.duck.duckbackend.entity.UserEntity;
import org.duck.duckbackend.model.BudgetModel;
import org.duck.duckbackend.repository.BudgetRepository;
import org.duck.duckbackend.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BudgetService {

    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;

    public String budgetSave(@AuthenticationPrincipal User user,
                             BudgetModel model) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername());
        BudgetEntity budgetEntity = BudgetModel.toEntity(model);
        budgetEntity.setUser(userEntity);
        budgetRepository.save(budgetEntity);
        return "OK";
    }
}
