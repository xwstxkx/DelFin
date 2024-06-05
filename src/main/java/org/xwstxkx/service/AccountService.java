package org.xwstxkx.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.model.BudgetModel;
import org.xwstxkx.model.MoneyModel;
import org.xwstxkx.repository.BudgetRepository;
import org.xwstxkx.service.security.UserService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AccountService {

    private final BudgetRepository budgetRepository;
    private final BudgetService budgetService;
    private final UserService userService;

    private UserEntity getUser() {
        return userService.getCurrentUser();
    }

    public MoneyModel countAllMoney() {
        List<BudgetEntity> entities = budgetRepository.findAllByUser(getUser());
        List<BudgetModel> models = BudgetModel.toListModel(entities);
        Long balance = 0L;
        Long total = 0L;
        Long spent = 0L;
        for (BudgetModel model : models) {
            MoneyModel moneyModel = budgetService.countBudgetBalance(model);
            spent += moneyModel.getSpent();
            balance += moneyModel.getBalance();
            total += moneyModel.getTotal();
        }
        return MoneyModel.builder()
                .spent(spent)
                .total(total)
                .balance(balance)
                .build();
    }
}
