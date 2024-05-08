package org.xwstxkx.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.entity.TransactionEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.TransactionModel;
import org.xwstxkx.repository.BudgetRepository;
import org.xwstxkx.repository.TransactionRepository;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;

    public void transferTransactionInBudget(Long transaction_id,
                                            Long budget_id) throws ObjectNotFound {
        BudgetEntity budgetEntity = budgetRepository
                .findById(budget_id).orElseThrow(ObjectNotFound::new);
        log.info("Бюджет найден");
        TransactionEntity transactionEntity = transactionRepository
                .findById(transaction_id).orElseThrow(ObjectNotFound::new);
        log.info("Транзакция найдена");
        transactionEntity.setBudget(budgetEntity);
        transactionRepository.save(transactionEntity);
        log.info("Транзакция была добавлена в бюджет");
    }

    public void transactionSave(TransactionModel model, Long id) throws ObjectNotFound {
        TransactionEntity transactionEntity = TransactionModel.toEntity(model);
        transactionEntity.setBudget(budgetRepository.findById(id).orElseThrow(ObjectNotFound::new));
        transactionRepository.save(transactionEntity);
        log.info("Транзакция была сохранена");
    }

    public List<TransactionModel> getAllBudgetTransactionsPage(PageRequest pageRequest, Long id)
            throws ObjectNotFound {
        BudgetEntity budgetEntity = budgetRepository.findById(id).orElseThrow(ObjectNotFound::new);
        Page<TransactionEntity> page = transactionRepository.findAllByBudget(budgetEntity, pageRequest);
        log.info("Лист транзакций бюджета был найден");
        return TransactionModel.toListModel(page.getContent());
    }

    public List<TransactionModel> getAllBudgetTransactions(Long id)
            throws ObjectNotFound {
        BudgetEntity budgetEntity = budgetRepository.findById(id).orElseThrow(ObjectNotFound::new);
        List<TransactionEntity> transactionEntities = transactionRepository.findAllByBudget(budgetEntity);
        log.info("Лист транзакций бюджета был найден");
        return TransactionModel.toListModel(transactionEntities);
    }

    public List<TransactionModel> getAllCategoryTransactions(String category) {
        List<TransactionEntity> transactionEntities = transactionRepository.findAllByCategory(category);
        log.info("Лист транзакций бюджета был найден");
        return TransactionModel.toListModel(transactionEntities);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
        log.info("Транзакция была удалена");
    }
}
