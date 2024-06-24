package org.xwstxkx.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.CategoryEntity;
import org.xwstxkx.entity.ExpenseEntity;
import org.xwstxkx.entity.IncomeEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.AccountModel;
import org.xwstxkx.model.UserModel;
import org.xwstxkx.repository.CategoryRepository;
import org.xwstxkx.repository.UserRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AccountService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private UserEntity getUser(Long id) throws ObjectNotFound {
        return userRepository.findById(id).orElseThrow(ObjectNotFound::new);
    }

    public Long allCategoryExpenses(LocalDate periodBegin, LocalDate periodEnd,
                                    Long id, UserModel userModel) throws ObjectNotFound {
        CategoryEntity category = categoryRepository.findByIdAndUser(id, getUser(userModel.getId()));
        return category.getExpenses().stream()
                .filter(incomeEntity -> incomeEntity.getDate().isAfter(periodBegin)
                        && incomeEntity.getDate().isBefore(periodEnd))
                .mapToLong(ExpenseEntity::getAmount).sum();
    }

    public AccountModel accountExpensesAndIncomesInPeriod(
            LocalDate periodBegin, LocalDate periodEnd,
            String title, UserModel userModel
    ) throws ObjectNotFound {
        Long total = getUser(userModel.getId()).getIncomes().stream()
                .filter(incomeEntity -> incomeEntity.getDate().isAfter(periodBegin)
                        && incomeEntity.getDate().isBefore(periodEnd))
                .mapToLong(IncomeEntity::getAmount).sum();
        Long spent = getUser(userModel.getId()).getExpenses().stream()
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
