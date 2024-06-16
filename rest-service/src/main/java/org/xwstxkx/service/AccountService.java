package org.xwstxkx.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.CategoryEntity;
import org.xwstxkx.entity.ExpenseEntity;
import org.xwstxkx.entity.IncomeEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.model.AccountModel;
import org.xwstxkx.repository.CategoryRepository;
import org.xwstxkx.service.security.UserService;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AccountService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    private UserEntity getUser() {
        return userService.getCurrentUser();
    }

    public Long allCategoryExpenses(LocalDate periodBegin, LocalDate periodEnd, Long id) {
        CategoryEntity category = categoryRepository.findByIdAndUser(id, getUser());
        return category.getExpenses().stream()
                .filter(incomeEntity -> incomeEntity.getDate().isAfter(periodBegin)
                        && incomeEntity.getDate().isBefore(periodEnd))
                .mapToLong(ExpenseEntity::getAmount).sum();
    }

    public AccountModel accountExpensesAndIncomesInPeriod(LocalDate periodBegin, LocalDate periodEnd,
                                                          String title) {
        Long total = getUser().getIncomes().stream()
                .filter(incomeEntity -> incomeEntity.getDate().isAfter(periodBegin)
                        && incomeEntity.getDate().isBefore(periodEnd))
                .mapToLong(IncomeEntity::getAmount).sum();
        Long spent = getUser().getExpenses().stream()
                .filter(incomeEntity -> incomeEntity.getDate().isAfter(periodBegin)
                        && incomeEntity.getDate().isBefore(periodEnd))
                .mapToLong(ExpenseEntity::getAmount).sum();
        return AccountModel.builder()
                .title(title)
                .total(total)
                .spent(spent)
                .balance(total - spent)
                .build();
    }
}
