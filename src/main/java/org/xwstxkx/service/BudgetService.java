package org.xwstxkx.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.exceptions.ObjectWithThisNameIsAlreadyExists;
import org.xwstxkx.model.BudgetModel;
import org.xwstxkx.model.TransactionModel;
import org.xwstxkx.repository.BudgetRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final TransactionService transactionService;
    private final UserService userService;

    public boolean isBudgetPeriodValid(Long id) throws ObjectNotFound {
        BudgetEntity budgetEntity = budgetRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFound("Бюджет не найден"));

        if (budgetEntity.getPeriodEnd().isBefore(LocalDate.now())) {
            log.info("Период данного бюджета истёк, " +
                    "оставшиеся средства направлены в накопительный бюджет");

            List<TransactionModel> transactionModels = transactionService
                    .getAllBudgetTransactions(budgetEntity.getId());
            for (TransactionModel model : transactionModels) {
                transactionService.transferTransactionInBudget(model.getId(),
                        findSavingsBudget().getId());
            }

            deleteBudget(id);
            log.info("Старый бюджет удалён");
            return false;
        }

        return true;
    }

    private BudgetEntity findSavingsBudget() {
        log.info("Поиск накопительного бюджета");
        return budgetRepository.findByTitleAndUser("Накопительный бюджет",
                userService.findUser());
    }

    public BudgetModel getBudget(Long id) throws ObjectNotFound {
        BudgetModel budgetModel = BudgetModel.toModel(findSavingsBudget());
        if (isBudgetPeriodValid(id)) {
            BudgetEntity budgetEntity = budgetRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFound("Бюджет не найден"));
            budgetModel = BudgetModel.toModel(budgetEntity);
        }
        return budgetModel;
    }

    public void budgetSave(BudgetModel model) throws ObjectNotFound, ObjectWithThisNameIsAlreadyExists {
        BudgetEntity budgetEntity = BudgetModel.toEntity(model);
        budgetEntity.setUser(userService.findUser());
        int count = 0;
        List<BudgetEntity> budgetEntities = budgetRepository.findAllByUser(userService.findUser());
        for (BudgetEntity entity : budgetEntities) {
            if (budgetEntity.getTitle().equals(entity.getTitle())) {
                count++;
                if (count > 1) {
                    throw new ObjectWithThisNameIsAlreadyExists();
                }
            }
        }
        budgetRepository.save(budgetEntity);
        log.info("Бюджет сохранён");
    }

    public void createBudgetSignup(UserEntity user) {
        BudgetEntity mainBudget = BudgetEntity.builder()
                .title("Основной бюджет")
                .periodEnd(LocalDate.now().plusMonths(1))
                .category("Общая категория")
                .sum(3000L)
                .user(user)
                .build();
        BudgetEntity accumulationBudget = BudgetEntity.builder()
                .title("Накопительный бюджет")
                .periodEnd(LocalDate.now().plusYears(5))
                .category("Общая категория")
                .sum(0L)
                .user(user)
                .build();
        budgetRepository.save(mainBudget);
        log.info("Создан основной бюджет для пользователя '{}'", user.getUsername());
        budgetRepository.save(accumulationBudget);
        log.info("Создан накопительный бюджет для пользователя '{}'", user.getUsername());
    }

    public List<BudgetModel> getAllBudgets(PageRequest pageRequest) {
        UserEntity userEntity = userService.getCurrentUser();
        Page<BudgetEntity> page = budgetRepository.findAllByUser(userEntity, pageRequest);
        log.info("Список бюджетов пользователя найден");
        return BudgetModel.toListModel(page.getContent());
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
        log.info("Бюджет успешно удалён");
    }
}
