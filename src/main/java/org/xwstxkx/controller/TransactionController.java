package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.exceptions.ParametersNotSpecified;
import org.xwstxkx.model.TransactionModel;
import org.xwstxkx.service.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/addTransactionInBudget")
    @Operation(summary = "Добавление транзакции в бюджет")
    public String addTransactionInBudget(@RequestParam Long transaction_id,
                                         @RequestParam Long budget_id) throws ObjectNotFound {
        transactionService.transferTransactionInBudget(transaction_id, budget_id);
        return "Операция проведена успешно";
    }

    @PostMapping("/saveTransaction")
    @Operation(summary = "Сохранение транзакции")
    public String saveTransaction(@RequestBody TransactionModel transactionModel,
                                  @RequestParam Long id) throws ObjectNotFound {
        transactionService.transactionSave(transactionModel, id);
        return "Транзакция успешно сохранена";
    }

    @PutMapping("/changeTransaction")
    @Operation(summary = "Изменение транзакции")
    public void patchTransaction(@RequestBody TransactionModel transactionModel,
                                 @RequestParam Long id) throws ObjectNotFound {
        transactionService.transactionSave(transactionModel, id);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Получение всех транзакций в бюджете")
    public ResponseEntity<List<TransactionModel>> getAllBudgetTransactions(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam Long id) throws ParametersNotSpecified, ObjectNotFound {
        if (page < 0 || size < 1) {
            throw new ParametersNotSpecified();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionService.getAllBudgetTransactionsPage(PageRequest.of(page, size), id));
    }

    @DeleteMapping
    @Operation(summary = "Удаление транзакции")
    public String deleteTransaction(@RequestParam Long id) {
        transactionService.deleteTransaction(id);
        return "Транзакция удалена успешно";
    }
}

