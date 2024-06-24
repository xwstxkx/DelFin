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
import org.xwstxkx.model.entity.ExpenseModel;
import org.xwstxkx.service.RestUserService;
import org.xwstxkx.service.crud.ExpensesCRUDService;
import org.xwstxkx.user.RestUser;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Расходы")
@RequestMapping("/expenses")
public class ExpensesController {

    private final ExpensesCRUDService expensesCRUDService;
    private final RestUserService restUserService;

    private RestUser getUser() {
        return restUserService.getCurrentUser();
    }

    @GetMapping("/getExpense/{id}")
    @Operation(summary = "Нахождение расхода")
    public ResponseEntity<ExpenseModel> getExpense(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expensesCRUDService.getExpense(id, UserModel.toModel(getUser())));
    }

    @PostMapping("/saveExpense/{category_id}/{budget_id}")
    @Operation(summary = "Сохранение расхода")
    public String saveExpense(@RequestBody ExpenseModel expenseModel,
                              @PathVariable Long category_id,
                              @PathVariable Long budget_id) throws ObjectNotFound, BadCredentials {
        return expensesCRUDService.saveExpense(expenseModel, category_id,
                budget_id, UserModel.toModel(getUser()));
    }

    @PutMapping("/changeExpense/{category_id}/{budget_id}")
    @Operation(summary = "Сохранение расхода")
    public String changeExpense(@RequestBody ExpenseModel expenseModel,
                                @PathVariable Long category_id,
                                @PathVariable Long budget_id) throws ObjectNotFound, BadCredentials {
        return expensesCRUDService.saveExpense(expenseModel, category_id,
                budget_id, UserModel.toModel(getUser()));
    }

    @DeleteMapping("/deleteExpense/{id}")
    @Operation(summary = "Удаление рахода")
    public String deleteExpense(@PathVariable Long id) throws ObjectNotFound {
        return expensesCRUDService.deleteExpense(id, UserModel.toModel(getUser()));
    }

    @GetMapping("/getAllExpenses")
    @Operation(summary = "Получение всех расходов")
    public ResponseEntity<List<ExpenseModel>> getAllExpenses() throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expensesCRUDService.getAllExpenses(UserModel.toModel(getUser())));
    }
}
