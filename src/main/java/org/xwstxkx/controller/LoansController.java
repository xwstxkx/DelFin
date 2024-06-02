package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.LoanModel;
import org.xwstxkx.service.crud.LoansCRUDService;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Займы")
@RequestMapping("/loans")
public class LoansController {

    private final LoansCRUDService loansCRUDService;

    @GetMapping("/getLoan/{id}")
    @Operation(summary = "Нахождение займа")
    public ResponseEntity<LoanModel> getLoan(@PathVariable Long id) throws ObjectNotFound {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansCRUDService.getLoan(id));
    }

    @PostMapping("/saveLoan")
    @Operation(summary = "Сохранение займа")
    public String saveBudget(@RequestBody LoanModel loanModel) throws ObjectNotFound, BadCredentials {
        return loansCRUDService.saveLoan(loanModel);
    }

    @PutMapping("/changeLoan")
    @Operation(summary = "Изменение займа")
    public String changeBudget(@RequestBody LoanModel loanModel) throws ObjectNotFound, BadCredentials {
        return loansCRUDService.saveLoan(loanModel);
    }

    @DeleteMapping("/deleteLoan")
    @Operation(summary = "Удаление займа")
    public String saveBudget(@RequestParam Long id) {
        return loansCRUDService.deleteLoan(id);
    }

    @GetMapping("/getAllLoans")
    @Operation(summary = "Получение всех займов")
    public ResponseEntity<List<LoanModel>> getAllBudgets() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansCRUDService.getAllLoans());
    }
}