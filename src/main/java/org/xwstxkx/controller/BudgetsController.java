package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.exceptions.ObjectWithThisNameIsAlreadyExists;
import org.xwstxkx.exceptions.ParametersNotSpecified;
import org.xwstxkx.model.BudgetModel;
import org.xwstxkx.service.crud.BudgetsCRUDService;

import java.util.List;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
@Tag(name = "Операции с бюджетами")
public class BudgetsController {

    private final BudgetsCRUDService budgetsCRUDService;

    @GetMapping("/getBudget/{id}")
    @Operation(summary = "Просмотр суммы бюджета")
    public BudgetModel getBudget(@PathVariable Long id) throws ObjectNotFound {
        return budgetsCRUDService.getBudget(id);
    }

    @PostMapping("/saveBudget/{category_id}")
    @Operation(summary = "Сохранение бюджета")
    public String saveBudget(@RequestBody BudgetModel budgetModel,
                             @PathVariable Long category_id) throws ObjectNotFound,
            ObjectWithThisNameIsAlreadyExists, BadCredentials {
        budgetsCRUDService.budgetSave(budgetModel, category_id);
        return "Бюджет успешно сохранён";
    }

    @GetMapping("/getAll")
    @Operation(summary = "Получение всех бюджетов")
    public ResponseEntity<List<BudgetModel>> getAllBudgets(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size)
            throws ParametersNotSpecified {
        if (page < 0 || size < 1) {
            throw new ParametersNotSpecified();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(budgetsCRUDService.getAllBudgets(PageRequest.of(page, size)));
    }

    @PutMapping("/changeBudget/{category_id}")
    @Operation(summary = "Сохранение бюджета")
    public String changeBudget(@RequestBody BudgetModel budgetModel,
                               @PathVariable Long category_id) throws ObjectNotFound,
            ObjectWithThisNameIsAlreadyExists, BadCredentials {
        budgetsCRUDService.budgetSave(budgetModel, category_id);
        return "Бюджет успешно сохранён";
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удаление бюджета")
    public String saveBudget(@PathVariable Long id) {
        budgetsCRUDService.deleteBudget(id);
        return "Бюджет успешно удалён";
    }
}
