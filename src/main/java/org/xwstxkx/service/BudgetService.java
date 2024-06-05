package org.xwstxkx.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.BudgetModel;
import org.xwstxkx.model.ExpenseModel;
import org.xwstxkx.model.IncomeModel;
import org.xwstxkx.model.MoneyModel;
import org.xwstxkx.repository.BudgetRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public boolean isBudgetValid(Long id) throws ObjectNotFound {
        BudgetEntity entity = budgetRepository.findById(id)
                .orElseThrow(ObjectNotFound::new);
        if (entity.getPeriodEnd().isBefore(LocalDate.now())) {
            return false;
        } else {
            return true;
        }
    }

    public MoneyModel countBudgetBalance(BudgetModel budgetModel) {
        List<IncomeModel> incomes = budgetModel.getIncomes();
        List<ExpenseModel> expenses = budgetModel.getExpenses();
        Long incomeSum = 0L;
        Long expenseSum = 0L;
        for (IncomeModel incomeModel : incomes) {
            incomeSum += incomeModel.getAmount();
            log.info("Сумма дохода : " + incomeSum);
        }
        for (ExpenseModel expenseModel : expenses) {
            expenseSum += expenseModel.getAmount();
            log.info("Сумма дохода : " + expenseSum);
        }
        return MoneyModel.builder()
                .total(incomeSum)
                .spent(expenseSum)
                .balance(incomeSum - expenseSum)
                .build();
    }

    public String budgetSavingAdd(boolean isRefreshed) {
        if (isRefreshed) {
            return "Бюджет продлён, остаток добавлен в накопительный бюджет";
        } else {
            return "Бюджет удалён, остаток добавлен в накопительный бюджет";
        }
    }

}
