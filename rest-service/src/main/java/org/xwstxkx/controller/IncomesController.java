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
import org.xwstxkx.model.entity.IncomeModel;
import org.xwstxkx.service.RestUserService;
import org.xwstxkx.service.crud.IncomesCRUDService;
import org.xwstxkx.user.RestUser;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Доходы")
@RequestMapping("/incomes")
public class IncomesController {

    private final IncomesCRUDService incomesCRUDService;
    private final RestUserService restUserService;

    private RestUser getUser() {
        return restUserService.getCurrentUser();
    }

    @GetMapping("/getIncome/{id}")
    @Operation(summary = "Нахождение дохода")
    public ResponseEntity<IncomeModel> getIncome(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(incomesCRUDService.getIncome(id, UserModel.toModel(getUser())));
    }

    @PostMapping("/saveIncome/{category_id}/{budget_id}")
    @Operation(summary = "Сохранение дохода")
    public String saveIncome(@RequestBody IncomeModel incomeModel,
                             @PathVariable Long category_id,
                             @PathVariable Long budget_id) throws ObjectNotFound, BadCredentials {
        return incomesCRUDService.saveIncome(incomeModel, category_id,
                budget_id, UserModel.toModel(getUser()));
    }

    @PutMapping("/changeIncome/{category_id}/{budget_id}")
    @Operation(summary = "Сохранение дохода")
    public String changeIncome(@RequestBody IncomeModel incomeModel,
                               @PathVariable Long category_id,
                               @PathVariable Long budget_id) throws ObjectNotFound, BadCredentials {
        return incomesCRUDService.saveIncome(incomeModel, category_id,
                budget_id, UserModel.toModel(getUser()));
    }

    @DeleteMapping("/deleteIncome/{id}")
    @Operation(summary = "Удаление дохода")
    public String deleteIncome(@PathVariable Long id) throws ObjectNotFound {
        return incomesCRUDService.deleteIncomes(id, UserModel.toModel(getUser()));
    }

    @GetMapping("/getAllIncomes")
    @Operation(summary = "Получение всех доходов")
    public ResponseEntity<List<IncomeModel>> getAllIncomes() throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(incomesCRUDService.getAllIncomes(UserModel.toModel(getUser())));
    }
}