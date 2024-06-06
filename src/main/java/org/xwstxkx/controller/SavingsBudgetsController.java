package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.model.entity.SavingsBudgetModel;
import org.xwstxkx.service.crud.SavingsBudgetsCRUDService;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Накопительные бюджеты")
@RequestMapping("/savingBudgets/")
public class SavingsBudgetsController {

    private final SavingsBudgetsCRUDService savingsBudgetsCRUDService;

    @GetMapping("/getSavingsBudget/{id}")
    @Operation(summary = "Нахождение накопительного бюджета")
    public ResponseEntity<SavingsBudgetModel> getSavingBudget(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(savingsBudgetsCRUDService.getSavingsBudget(id));
    }

    @PostMapping("/saveSavingsBudget")
    @Operation(summary = "Сохранение накопительного бюджета")
    public String saveSavingBudget(@RequestBody SavingsBudgetModel savingBudgetModel) throws BadCredentials {
        return savingsBudgetsCRUDService.saveSavingsBudget(savingBudgetModel);
    }

    @PutMapping("/changeSavingsBudget")
    @Operation(summary = "Сохранение накопительного бюджета")
    public String changeSavingBudget(@RequestBody SavingsBudgetModel savingBudgetModel) throws BadCredentials {
        return savingsBudgetsCRUDService.saveSavingsBudget(savingBudgetModel);
    }

    @DeleteMapping("/deleteSavingsBudget/{id}")
    @Operation(summary = "Удаление накопительного бюджета")
    public String deleteSavingBudget(@PathVariable Long id) {
        return savingsBudgetsCRUDService.deleteSavingsBudget(id);
    }

    @GetMapping("/getAllSavingsBudgets")
    @Operation(summary = "Получение всех накопительных бюджетов")
    public ResponseEntity<List<SavingsBudgetModel>> getAllSavingBudgets() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(savingsBudgetsCRUDService.getAllSavingBudgets());
    }
}
