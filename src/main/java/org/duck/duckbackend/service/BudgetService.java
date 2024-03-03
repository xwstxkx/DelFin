package org.duck.duckbackend.service;

import lombok.AllArgsConstructor;
import org.duck.duckbackend.entity.BudgetEntity;
import org.duck.duckbackend.entity.TransactionEntity;
import org.duck.duckbackend.exceptions.ObjectNotFound;
import org.duck.duckbackend.model.BudgetModel;
import org.duck.duckbackend.model.BudgetResponse;
import org.duck.duckbackend.repository.BudgetRepository;
import org.duck.duckbackend.repository.TransactionRepository;
import org.duck.duckbackend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BudgetService {

    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public BudgetResponse budgetBalance(Long id) throws ObjectNotFound {
        BudgetEntity budgetEntity = budgetRepository.findById(id)
                .orElseThrow(ObjectNotFound::new);
        Long sum = budgetEntity.getSum();
        Long spentAmount = 0L;
        Long balance = 0L;
        List<TransactionEntity> transactionEntities = transactionRepository.findAll();
        for (TransactionEntity entity : transactionEntities) {
            spentAmount += entity.getSum();
        }
        balance = sum - spentAmount;
        return BudgetResponse.builder()
                .amount(sum)
                .spentAmount(spentAmount)
                .balance(balance)
                .build();
    }

    public String budgetSave(BudgetModel model) {
        BudgetEntity budgetEntity = BudgetModel.toEntity(model);
        budgetEntity.setUser(userService.findUser());
        budgetRepository.save(budgetEntity);
        return "OK";
    }

    public List<BudgetModel> getAllBudgets(PageRequest pageRequest) {
        Page<BudgetEntity> page = budgetRepository.findAll(pageRequest);
        return BudgetModel.toListModel(page.getContent());
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}
