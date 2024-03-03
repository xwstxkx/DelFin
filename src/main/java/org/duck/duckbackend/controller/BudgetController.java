package org.duck.duckbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.duck.duckbackend.exceptions.ObjectNotFound;
import org.duck.duckbackend.exceptions.ParametersNotSpecified;
import org.duck.duckbackend.model.BudgetModel;
import org.duck.duckbackend.model.BudgetResponse;
import org.duck.duckbackend.service.BudgetService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budget")
@RequiredArgsConstructor
@Tag(name = "Операции с бюджетами")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/balance")
    @Operation(summary = "Просмотр суммы бюджета")
    public BudgetResponse budgetBalance(@RequestParam Long id) throws ObjectNotFound {
        return budgetService.budgetBalance(id);
    }

    @PostMapping("/save")
    @Operation(summary = "Сохранение бюджета")
    public String saveBudget(@RequestBody BudgetModel budgetModel) {
        budgetService.budgetSave(budgetModel);
        return "Бюджет успешно сохранён";
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Сохранение бюджета")
    public String saveBudget(@RequestParam Long id) {
        budgetService.deleteBudget(id);
        return "Бюджет успешно удалён";
    }

    @GetMapping("/getAll")
    @Operation(summary = "Получение всех школ")
    public ResponseEntity<List<BudgetModel>> getAllSchools(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size)
            throws ParametersNotSpecified {
        if (page < 0 || size < 1) {
            throw new ParametersNotSpecified();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(budgetService.getAllBudgets(PageRequest.of(page, size)));
    }
}
