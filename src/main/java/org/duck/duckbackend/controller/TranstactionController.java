package org.duck.duckbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.duck.duckbackend.exceptions.ObjectNotFound;
import org.duck.duckbackend.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TranstactionController {

    private final TransactionService transactionService;

    @PostMapping("/addTransactionInBudget")
    @Operation(summary = "Добавление транзакции в бюджет")
    public String addTransactionInBudget(@RequestParam Long transaction_id,
                                         @RequestParam Long budget_id) throws ObjectNotFound {
        transactionService.addTransactionInBudget(transaction_id, budget_id);
        return "Операция проведена успешно";
    }
}
