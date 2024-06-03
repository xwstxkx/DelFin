package org.xwstxkx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.model.DebtsModel;
import org.xwstxkx.service.crud.DebtsCRUDService;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Долги")
@RequestMapping("/debts")
public class DebtsController {

    private final DebtsCRUDService debtsCRUDService;

    @GetMapping("/getDebt/{id}")
    @Operation(summary = "Нахождение долга")
    public ResponseEntity<DebtsModel> getDebt(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(debtsCRUDService.getDebt(id));
    }

    @PostMapping("/saveDebt")
    @Operation(summary = "Сохранение долга")
    public String saveDebt(@RequestBody DebtsModel debtModel)
            throws BadCredentials {
        return debtsCRUDService.saveDebt(debtModel);
    }

    @PutMapping("/changeDebt")
    @Operation(summary = "Сохранение долга")
    public String changeDebt(@RequestBody DebtsModel debtModel)
            throws BadCredentials {
        return debtsCRUDService.saveDebt(debtModel);
    }

    @DeleteMapping("/deleteDebt/{id}")
    @Operation(summary = "Удаление долга")
    public String saveDebt(@PathVariable Long id) {
        return debtsCRUDService.deleteDebt(id);
    }

    @GetMapping("/getAllDebts")
    @Operation(summary = "Получение всех долгов")
    public ResponseEntity<List<DebtsModel>> getAllDebts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(debtsCRUDService.getAllDebts());
    }
}
