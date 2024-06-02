package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.IncomeModel;
import org.xwstxkx.service.crud.IncomesCRUDService;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Доходы")
@RequestMapping("/incomes")
public class IncomesController {

    private final IncomesCRUDService incomesCRUDService;

    @GetMapping("/getIncome/{id}")
    @Operation(summary = "Нахождение дохода")
    public ResponseEntity<IncomeModel> getIncome(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(incomesCRUDService.getIncome(id));
    }

    @PostMapping("/saveIncome/{category_id}")
    @Operation(summary = "Сохранение дохода")
    public String saveIncome(@RequestBody IncomeModel incomeModel,
                             @PathVariable Long category_id) throws ObjectNotFound, BadCredentials {
        return incomesCRUDService.saveIncome(incomeModel, category_id);
    }

    @PutMapping("/changeIncome/{category_id}")
    @Operation(summary = "Сохранение дохода")
    public String changeIncome(@RequestBody IncomeModel incomeModel,
                               @PathVariable Long category_id) throws ObjectNotFound, BadCredentials {
        return incomesCRUDService.saveIncome(incomeModel, category_id);
    }

    @DeleteMapping("/deleteIncome/{id}")
    @Operation(summary = "Удаление дохода")
    public String saveBudget(@PathVariable Long id) {
        return incomesCRUDService.deleteIncomes(id);
    }

    @GetMapping("/getAllIncomes")
    @Operation(summary = "Получение всех доходов")
    public ResponseEntity<List<IncomeModel>> getAllIncomes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(incomesCRUDService.getAllIncomes());
    }
}