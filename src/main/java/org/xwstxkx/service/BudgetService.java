//package org.xwstxkx.service;
//
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.xwstxkx.repository.BudgetRepository;
//
//@Service
//@AllArgsConstructor
//public class BudgetService {
//
//    private final BudgetRepository budgetRepository;
//
//
////    public boolean isBudgetPeriodValid(Long id) throws ObjectNotFound {
////        BudgetEntity budgetEntity = budgetRepository.findById(id)
////                .orElseThrow(() -> new ObjectNotFound("Бюджет не найден"));
////
////        if (budgetEntity.getPeriodEnd().isBefore(LocalDate.now())) {
////            log.info("Период данного бюджета истёк, " +
////                    "оставшиеся средства направлены в накопительный бюджет");
////
////            List<TransactionModel> transactionModels = transactionService
////                    .getAllBudgetTransactions(budgetEntity.getId());
////            for (TransactionModel model : transactionModels) {
////                transactionService.transferTransactionInBudget(model.getId(),
////                        findSavingsBudget().getId());
////            }
////
////            deleteBudget(id);
////            log.info("Старый бюджет удалён");
////            return false;
////        }
////
////        return true;
////    }
////    public BudgetModel getBudget(Long id) throws ObjectNotFound {
////        BudgetModel budgetModel = BudgetModel.toModel(findSavingsBudget());
////        if (isBudgetPeriodValid(id)) {
////            BudgetEntity budgetEntity = budgetRepository.findById(id)
////                    .orElseThrow(() -> new ObjectNotFound("Бюджет не найден"));
////            budgetModel = BudgetModel.toModel(budgetEntity);
////        }
////        return budgetModel;
////    }
//
//}
