package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.UserModel;
import org.xwstxkx.model.entity.SavingsBudgetModel;
import org.xwstxkx.service.RestUserService;
import org.xwstxkx.service.crud.SavingsBudgetsCRUDService;
import org.xwstxkx.user.RestUser;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Накопительные бюджеты")
@RequestMapping("/savingBudgets/")
public class SavingsBudgetsController {

    private final SavingsBudgetsCRUDService savingsBudgetsCRUDService;
    private final RestUserService restUserService;

    private RestUser getUser() {
        return restUserService.getCurrentUser();
    }
    @GetMapping("/getSavingsBudget/{id}")
    @Operation(summary = "Нахождение накопительного бюджета")
    public ResponseEntity<SavingsBudgetModel> getSavingBudget(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(savingsBudgetsCRUDService.getSavingsBudget(id, UserModel.toModel(getUser())));
    }

    @PostMapping("/saveSavingsBudget")
    @Operation(summary = "Сохранение накопительного бюджета")
    public String saveSavingBudget(@RequestBody SavingsBudgetModel savingBudgetModel) throws BadCredentials, ObjectNotFound {
        return savingsBudgetsCRUDService.saveSavingsBudget(savingBudgetModel, UserModel.toModel(getUser()));
    }

    @PutMapping("/changeSavingsBudget")
    @Operation(summary = "Сохранение накопительного бюджета")
    public String changeSavingBudget(@RequestBody SavingsBudgetModel savingBudgetModel) throws BadCredentials, ObjectNotFound {
        return savingsBudgetsCRUDService.saveSavingsBudget(savingBudgetModel, UserModel.toModel(getUser()));
    }

    @DeleteMapping("/deleteSavingsBudget/{id}")
    @Operation(summary = "Удаление накопительного бюджета")
    public String deleteSavingBudget(@PathVariable Long id) throws ObjectNotFound {
        return savingsBudgetsCRUDService.deleteSavingsBudget(id, UserModel.toModel(getUser()));
    }

    @GetMapping("/getAllSavingsBudgets")
    @Operation(summary = "Получение всех накопительных бюджетов")
    public ResponseEntity<List<SavingsBudgetModel>> getAllSavingBudgets() throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(savingsBudgetsCRUDService.getAllSavingBudgets(UserModel.toModel(getUser())));
    }
}
