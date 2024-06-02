package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.ExpenseModel;
import org.xwstxkx.service.crud.ExpensesCRUDService;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Расходы")
@RequestMapping("/expenses")
public class ExpensesController {

    private final ExpensesCRUDService expensesCRUDService;

    @GetMapping("/getExpense/{id}")
    @Operation(summary = "Нахождение расхода")
    public ResponseEntity<ExpenseModel> getExpense(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expensesCRUDService.getExpense(id));
    }

    @PostMapping("/saveExpense/{category_id}")
    @Operation(summary = "Сохранение расхода")
    public String saveExpense(@RequestBody ExpenseModel expenseModel,
                              @PathVariable Long category_id) throws ObjectNotFound, BadCredentials {
        return expensesCRUDService.saveExpense(expenseModel, category_id);
    }

    @PutMapping("/changeExpense/{category_id}")
    @Operation(summary = "Сохранение расхода")
    public String changeExpense(@RequestBody ExpenseModel expenseModel,
                                @PathVariable Long category_id) throws ObjectNotFound, BadCredentials {
        return expensesCRUDService.saveExpense(expenseModel, category_id);
    }

    @DeleteMapping("/deleteExpense")
    @Operation(summary = "Удаление рахода")
    public String saveBudget(@RequestParam Long id) {
        return expensesCRUDService.deleteExpense(id);
    }

    @GetMapping("/getAllExpenses")
    @Operation(summary = "Получение всех расходов")
    public ResponseEntity<List<ExpenseModel>> getAllExpenses() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expensesCRUDService.getAllExpenses());
    }
}
