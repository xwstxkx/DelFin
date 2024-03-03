package org.duck.duckbackend.service;

import lombok.AllArgsConstructor;
import org.duck.duckbackend.entity.BudgetEntity;
import org.duck.duckbackend.entity.TransactionEntity;
import org.duck.duckbackend.entity.UserEntity;
import org.duck.duckbackend.exceptions.ObjectNotFound;
import org.duck.duckbackend.model.TransactionModel;
import org.duck.duckbackend.repository.BudgetRepository;
import org.duck.duckbackend.repository.TransactionRepository;
import org.duck.duckbackend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    public String addTransactionInBudget(Long transaction_id,
                                         Long budget_id) throws ObjectNotFound {
        BudgetEntity budgetEntity = budgetRepository
                .findById(budget_id).orElseThrow(ObjectNotFound::new);
        TransactionEntity transactionEntity = transactionRepository
                .findById(transaction_id).orElseThrow(ObjectNotFound::new);
        transactionEntity.setBudget(budgetEntity);
        transactionRepository.save(transactionEntity);
        return "Операция проведена успешно";
    }

    public void transactionSave(TransactionModel model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserEntity userEntity = userRepository.findByUsername(currentPrincipalName);
        TransactionEntity transactionEntity = TransactionModel.toEntity(model);
        transactionEntity.setUser(userEntity);
        transactionRepository.save(transactionEntity);
    }

    public List<TransactionModel> getAllTransactions(PageRequest pageRequest) {
        Page<TransactionEntity> page = transactionRepository.findAll(pageRequest);
        return TransactionModel.toListModel(page.getContent());
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
