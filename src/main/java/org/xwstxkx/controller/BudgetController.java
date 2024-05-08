package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.exceptions.ObjectWithThisNameIsAlreadyExists;
import org.xwstxkx.exceptions.ParametersNotSpecified;
import org.xwstxkx.model.BudgetModel;
import org.xwstxkx.service.BudgetService;

import java.util.List;

@RestController
@RequestMapping("/budget")
@RequiredArgsConstructor
@Tag(name = "Операции с бюджетами")
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping("/balance")
    @Operation(summary = "Просмотр суммы бюджета")
    public BudgetModel budgetBalance(@RequestParam Long id) throws ObjectNotFound {
        return budgetService.getBudget(id);
    }

    @PostMapping("/saveBudget")
    @Operation(summary = "Сохранение бюджета")
    public String saveBudget(@RequestBody BudgetModel budgetModel) throws ObjectNotFound,
            ObjectWithThisNameIsAlreadyExists {
        budgetService.budgetSave(budgetModel);
        return "Бюджет успешно сохранён";
    }

    @PutMapping("/changeBudget")
    @Operation(summary = "Сохранение бюджета")
    public String changeBudget(@RequestBody BudgetModel budgetModel) throws ObjectNotFound,
            ObjectWithThisNameIsAlreadyExists {
        budgetService.budgetSave(budgetModel);
        return "Бюджет успешно сохранён";
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удаление бюджета")
    public String saveBudget(@RequestParam Long id) {
        budgetService.deleteBudget(id);
        return "Бюджет успешно удалён";
    }

    @GetMapping("/getAll")
    @Operation(summary = "Получение всех бюджетов")
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
